/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modele.metier.Country;

/**
 *
 * @author Benoit
 */
public class CountriesDao {

    /**
     * Extraction de tous les pays
     *
     * @return collection de pays
     * @throws SQLException
     */
    public static ArrayList<Country> selectAll() throws SQLException {
        ArrayList<Country> countries = new ArrayList<>();
        Country aCountry;
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "SELECT * FROM countries";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String countryCode = rs.getString("countryCode");
            String countryName = rs.getString("countryName");
            String currencyCode = rs.getString("currencyCode");
            int population = rs.getInt("population");
            String fipsCode = rs.getString("fipsCode");
            int isoNumeric = rs.getInt("isoNumeric");
            float north = rs.getFloat("north");
            float south = rs.getFloat("south");
            float east = rs.getFloat("east");
            float west = rs.getFloat("west");
            String capital = rs.getString("capital");
            String continentName = rs.getString("continentName");
            String continent = rs.getString("continent");
            int areaInSqKm = rs.getInt("areaInSqKm");
            String languages = rs.getString("languages");
            String isoAlpha3 = rs.getString("isoAlpha3");
            int geonameId = rs.getInt("geonameId");
            String flag = rs.getString("flag");
            int level = rs.getInt("lvl");
            String place = rs.getString("places");
            aCountry = new Country(id, countryCode, countryName, currencyCode, population, fipsCode, isoNumeric, north, south, east, west, capital, continentName, continent, areaInSqKm, languages, isoAlpha3, geonameId, flag, level, place);
            countries.add(aCountry);
        }
        rs.close();
        pstmt.close();
        return countries;
    }

    /**
     * Extraction de tous les pays selon leurs continents
     *
     * @param continentCode
     * @return collection de pays
     * @throws SQLException
     */
    public static ArrayList<Country> selectAllByContinentCode(String continentCode) throws SQLException {
        ArrayList<Country> countries = new ArrayList<>();
        Country aCountry;
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "SELECT * FROM countries WHERE continent=?";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setString(1, continentCode);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String countryCode = rs.getString("countryCode");
            String countryName = rs.getString("countryName");
            String currencyCode = rs.getString("currencyCode");
            int population = rs.getInt("population");
            String fipsCode = rs.getString("fipsCode");
            int isoNumeric = rs.getInt("isoNumeric");
            float north = rs.getFloat("north");
            float south = rs.getFloat("south");
            float east = rs.getFloat("east");
            float west = rs.getFloat("west");
            String capital = rs.getString("capital");
            String continentName = rs.getString("continentName");
            String continent = rs.getString("continent");
            int areaInSqKm = rs.getInt("areaInSqKm");
            String languages = rs.getString("languages");
            String isoAlpha3 = rs.getString("isoAlpha3");
            int geonameId = rs.getInt("geonameId");
            String flag = rs.getString("flag");
            int level = rs.getInt("lvl");
            String place = rs.getString("places");
            aCountry = new Country(id, countryCode, countryName, currencyCode, population, fipsCode, isoNumeric, north, south, east, west, capital, continentName, continent, areaInSqKm, languages, isoAlpha3, geonameId, flag, level, place);
            countries.add(aCountry);
        }
        rs.close();
        pstmt.close();
        return countries;
    }

    /**
     * Extraction de tous les pays selon leurs difficulté
     *
     * @param level
     * @return collection de pays
     * @throws SQLException
     */
    public static ArrayList<Country> selectSelectAllByLevel(int level) throws SQLException {
        ArrayList<Country> countries = new ArrayList<>();
        Country aCountry;
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "SELECT * FROM countries WHERE lvl<=?";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setInt(1, level);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String countryCode = rs.getString("countryCode");
            String countryName = rs.getString("countryName");
            String currencyCode = rs.getString("currencyCode");
            int population = rs.getInt("population");
            String fipsCode = rs.getString("fipsCode");
            int isoNumeric = rs.getInt("isoNumeric");
            float north = rs.getFloat("north");
            float south = rs.getFloat("south");
            float east = rs.getFloat("east");
            float west = rs.getFloat("west");
            String capital = rs.getString("capital");
            String continentName = rs.getString("continentName");
            String continent = rs.getString("continent");
            int areaInSqKm = rs.getInt("areaInSqKm");
            String languages = rs.getString("languages");
            String isoAlpha3 = rs.getString("isoAlpha3");
            int geonameId = rs.getInt("geonameId");
            String flag = rs.getString("flag");
            int leLevel = rs.getInt("lvl");
            String place = rs.getString("places");
            aCountry = new Country(id, countryCode, countryName, currencyCode, population, fipsCode, isoNumeric, north, south, east, west, capital, continentName, continent, areaInSqKm, languages, isoAlpha3, geonameId, flag, leLevel, place);
            countries.add(aCountry);
        }
        rs.close();
        pstmt.close();
        return countries;
    }

    /**
     * Extraction de tous les pays selon leurs difficulté et leurs continent
     *
     * @param level
     * @param continent
     * @return collection de pays
     * @throws SQLException
     */
    public static ArrayList<Country> selectSelectAllByLevelAndContinent(int level, String continent) throws SQLException {
        ArrayList<Country> countries = new ArrayList<>();
        Country aCountry;
        ResultSet rs;
        PreparedStatement pstmt;
        Jdbc jdbc = Jdbc.getInstance();
        // préparer la requête
        String requete = "SELECT * FROM countries WHERE lvl=? AND continent=?";
        pstmt = jdbc.getConnexion().prepareStatement(requete);
        pstmt.setInt(1, level);
        pstmt.setString(2, continent);
        rs = pstmt.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String countryCode = rs.getString("countryCode");
            String countryName = rs.getString("countryName");
            String currencyCode = rs.getString("currencyCode");
            int population = rs.getInt("population");
            String fipsCode = rs.getString("fipsCode");
            int isoNumeric = rs.getInt("isoNumeric");
            float north = rs.getFloat("north");
            float south = rs.getFloat("south");
            float east = rs.getFloat("east");
            float west = rs.getFloat("west");
            String capital = rs.getString("capital");
            String continentName = rs.getString("continentName");
            String leContinent = rs.getString("continent");
            int areaInSqKm = rs.getInt("areaInSqKm");
            String languages = rs.getString("languages");
            String isoAlpha3 = rs.getString("isoAlpha3");
            int geonameId = rs.getInt("geonameId");
            String flag = rs.getString("flag");
            int leLevel = rs.getInt("lvl");
            String place = rs.getString("places");
            aCountry = new Country(id, countryCode, countryName, currencyCode, population, fipsCode, isoNumeric, north, south, east, west, capital, continentName, leContinent, areaInSqKm, languages, isoAlpha3, geonameId, flag, leLevel, place);
            countries.add(aCountry);
        }
        rs.close();
        pstmt.close();
        return countries;
    }
}
