package com.mindnotix.model;

/**
 * Created by Admin on 05-03-2018.
 */

public class HandleFilterPojo {

    String spsubcategories;

    public String getSpsubcategories() {
        return spsubcategories;
    }

    public void setSpsubcategories(String spsubcategories) {
        this.spsubcategories = spsubcategories;
    }

    public String getSpjobtype() {
        return spjobtype;
    }

    public void setSpjobtype(String spjobtype) {
        this.spjobtype = spjobtype;
    }

    public String getSpregion() {
        return spregion;
    }

    public void setSpregion(String spregion) {
        this.spregion = spregion;
    }

    public String getSpdistrict() {
        return spdistrict;
    }

    public void setSpdistrict(String spdistrict) {
        this.spdistrict = spdistrict;
    }

    public String getSpsalary() {
        return spsalary;
    }

    public void setSpsalary(String spsalary) {
        this.spsalary = spsalary;
    }

    public String getSpfrom() {
        return spfrom;
    }

    public void setSpfrom(String spfrom) {
        this.spfrom = spfrom;
    }

    public String getSpto() {
        return spto;
    }

    public void setSpto(String spto) {
        this.spto = spto;
    }

    public String getSpsort() {
        return spsort;
    }

    public void setSpsort(String spsort) {
        this.spsort = spsort;
    }

    public boolean isChkfav() {
        return chkfav;
    }

    public void setChkfav(boolean chkfav) {
        this.chkfav = chkfav;
    }

    String spjobtype;
    String spregion;
    String spdistrict;
    String spsalary;
    String spfrom;
    String spto;
    String spsort;
    boolean chkfav;


    public String getEdtJobSearch() {
        return edtJobSearch;
    }

    public void setEdtJobSearch(String edtJobSearch) {
        this.edtJobSearch = edtJobSearch;
    }

    private String edtJobSearch;

    public String getJopCatagoreyPosition() {
        return JopCatagoreyPosition;
    }

    public void setJopCatagoreyPosition(String jopCatagoreyPosition) {
        JopCatagoreyPosition = jopCatagoreyPosition;
    }

    private String JopCatagoreyPosition="0";
}
