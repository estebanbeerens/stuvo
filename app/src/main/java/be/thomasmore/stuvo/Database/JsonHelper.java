package be.thomasmore.stuvo.Database;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonHelper {
//    EXAMPELS FROM LES 5 SOCCER DEZE DUS AANPASSEN NAAR WAT WIJ NODIG HEBBEN!!!!
//    public List<Competitie> getCompetities(String jsonTekst) {
//        List<Competitie> competities = new ArrayList<Competitie>();
//
//        try {
//            JSONObject jsonObjectCompetities = new JSONObject(jsonTekst);
//            JSONArray jsonArrayCompetities = new JSONArray(jsonObjectCompetities.getString("Competities"));
//
//            for (int i = 0; i < jsonArrayCompetities.length(); i++) {
//                JSONObject jsonObjectCompetitie = jsonArrayCompetities.getJSONObject(i);
//
//                Competitie competitie = new Competitie();
//                competitie.setId(jsonObjectCompetitie.getInt("Id"));
//                competitie.setNaam(jsonObjectCompetitie.getString("Caption"));
//                competities.add(competitie);
//            }
//        } catch (JSONException e) {
//            Log.e("JSON Parser", "Error parsing data " + e.toString());
//        }
//        return competities;
//    }
//
//    public List<Ploeg> getPloegen(String jsonTekst) {
//        List<Ploeg> ploegen = new ArrayList<Ploeg>();
//
//        try {
//            JSONArray jsonArrayPloegen = new JSONArray(jsonTekst);
//
//            for (int i = 0; i < jsonArrayPloegen.length(); i++) {
//                JSONObject jsonObjectploeg = jsonArrayPloegen.getJSONObject(i);
//                JSONObject jsonObjectPloegNames = new JSONObject(jsonObjectploeg.getString("Names"));
////                JSONArray jsonArrayNames = new JSONArray(jsonObjectNames.getString("Names"));
//
//                String ploegnaam = jsonObjectPloegNames.getString("Full");
//
//                Ploeg ploeg = new Ploeg();
//                ploeg.setId(jsonObjectploeg.getInt("Id"));
//                ploeg.setNaam(ploegnaam);
//                ploegen.add(ploeg);
//            }
//        } catch (JSONException e) {
//            Log.e("JSON Parser", "Error parsing data " + e.toString());
//        }
//        return ploegen;
//    }
}