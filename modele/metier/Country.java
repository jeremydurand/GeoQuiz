/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.metier;

/**
 *
 * @author Benoit
 */
public class Country {

    private int id;
    private String countryCode;
    private String countryName;
    private String currencyCode;
    private int population;
    private String fipsCode;
    private int isoNumeric;
    private float north;
    private float south;
    private float east;
    private float west;
    private String capital;
    private String continentName;
    private String continent;
    private int areaInSqKm;
    private String languages;
    private String isoAlpha3;
    private int geonameId;
    private String flag;
    private int level;
    private String place;

    public Country(int id, String countryCode, String countryName, String currencyCode, int population, String fipsCode, int isoNumeric, float north, float south, float east, float west, String capital, String continentName, String continent, int areaInSqKm, String languages, String isoAlpha3, int geonameId, String flag, int level, String place) {
        this.id = id;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.currencyCode = currencyCode;
        this.population = population;
        this.fipsCode = fipsCode;
        this.isoNumeric = isoNumeric;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
        this.capital = capital;
        this.continentName = continentName;
        this.continent = continent;
        this.areaInSqKm = areaInSqKm;
        this.languages = languages;
        this.isoAlpha3 = isoAlpha3;
        this.geonameId = geonameId;
        this.flag = flag;
        this.level = level;
        this.place = place;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getFipsCode() {
        return fipsCode;
    }

    public void setFipsCode(String fipsCode) {
        this.fipsCode = fipsCode;
    }

    public int getIsoNumeric() {
        return isoNumeric;
    }

    public void setIsoNumeric(int isoNumeric) {
        this.isoNumeric = isoNumeric;
    }

    public float getNorth() {
        return north;
    }

    public void setNorth(float north) {
        this.north = north;
    }

    public float getSouth() {
        return south;
    }

    public void setSouth(float south) {
        this.south = south;
    }

    public float getEast() {
        return east;
    }

    public void setEast(float east) {
        this.east = east;
    }

    public float getWest() {
        return west;
    }

    public void setWest(float west) {
        this.west = west;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public int getAreaInSqKm() {
        return areaInSqKm;
    }

    public void setAreaInSqKm(int areaInSqKm) {
        this.areaInSqKm = areaInSqKm;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getIsoAlpha3() {
        return isoAlpha3;
    }

    public void setIsoAlpha3(String isoAlpha3) {
        this.isoAlpha3 = isoAlpha3;
    }

    public int getGeonameId() {
        return geonameId;
    }

    public void setGeonameId(int geonameId) {
        this.geonameId = geonameId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "Country{" + "id=" + id + ", countryCode=" + countryCode + ", countryName=" + countryName + ", currencyCode=" + currencyCode + ", population=" + population + ", fipsCode=" + fipsCode + ", isoNumeric=" + isoNumeric + ", north=" + north + ", south=" + south + ", east=" + east + ", west=" + west + ", capital=" + capital + ", continentName=" + continentName + ", continent=" + continent + ", areaInSqKm=" + areaInSqKm + ", languages=" + languages + ", isoAlpha3=" + isoAlpha3 + ", geonameId=" + geonameId + '}';
    }

}
