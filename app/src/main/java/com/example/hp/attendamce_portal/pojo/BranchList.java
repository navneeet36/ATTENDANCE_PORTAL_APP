package com.example.hp.attendamce_portal.pojo;



public class BranchList {
    private String branchid;
    private String branchname;
    private String totalsem;

    public BranchList(String branchid, String branchname,String totalsem) {
        this.branchid = branchid;
        this.branchname = branchname;
        this.totalsem =totalsem ;
    }
    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getTotalsem() {
        return totalsem;
    }

    public void setTotalsem(String totalsem) {
        this.totalsem = totalsem;
    }
}
