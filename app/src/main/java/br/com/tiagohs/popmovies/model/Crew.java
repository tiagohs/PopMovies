package br.com.tiagohs.popmovies.model;

/*
 "credit_id": "52fe4250c3a36847f80149ef",
      "department": "Writing",
      "id": 7469,
      "job": "Author",
      "name": "Jim Uhls",
      "profile_path": null
 */
public class Crew {
    private String credit_id;
    private String department;
    private int id;
    private String job;
    private String name;
    private String profile_path;

    public Crew(String credit_id, String department, int id, String job, String name, String profile_path) {
        this.credit_id = credit_id;
        this.department = department;
        this.id = id;
        this.job = job;
        this.name = name;
        this.profile_path = profile_path;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }
}
