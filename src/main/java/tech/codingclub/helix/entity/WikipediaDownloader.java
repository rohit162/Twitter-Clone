package tech.codingclub.helix.entity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import tech.codingclub.helix.global.HttpURLConnectionExample;

public class  WikipediaDownloader {

    private String keyword;

    public WikipediaDownloader(){}

    public WikipediaDownloader(String keyword) {
        this.keyword = keyword;
    }


    public WikiResult getResult() {
        //1. clean or process keyword
        //2. get the URL for wikipedia
        //3. Make a GET request to wikipedia
        //4. Parsing the useful result using Jsoup
        //5. Showing results

        if(this.keyword==null || this.keyword.length()==0){
            return null;
        }
        //Step 1
        this.keyword = this.keyword.trim().replaceAll("[ ]+","_");

        //Step 2
        String wikiUrl = getWikipediaUrlForQuery(this.keyword);
        String response = "";
        String imgURL="";
        try {
            //Step 3
            String wikipediaResponseHTML = HttpURLConnectionExample.sendGet(wikiUrl);
            //System.out.println(wikipediaResponseHTML);

            //Step 4
            Document document = Jsoup.parse(wikipediaResponseHTML,"https://en.wikipedia.org");
            Elements childElements = document.body().select(".mw-parser-output > *");

            int state=0;
            for(Element childElement : childElements){
                if(state==0){
                    if(childElement.tagName().equals("table")){
                        state=1;
                    } }
                else if(state==1){
                    if(childElement.tagName().equals("p")){
                        state=2;
                        response = childElement.text();

                        break;
                    }
                }
                //System.out.println(childElement.tagName());
            }
            try {
                imgURL = document.body().select(".infobox img").get(0).attr("src");
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            }
            catch (Exception e) {
            e.printStackTrace();
        }
        WikiResult wikiResult = new WikiResult(this.keyword,response,imgURL);
        //Push data to database
        System.out.println(new Gson().toJson(wikiResult));

        // to get wikiResultGson
//        Gson gson= new GsonBuilder().setPrettyPrinting().create();
//        String json=gson.toJson(wikiResult);
//       return json;

        // to get wikiResultHtml
        return wikiResult;

    }

    private String getWikipediaUrlForQuery(String cleanKeyWord) {
        return "https://en.wikipedia.org/wiki/"+cleanKeyWord;
    }


}
