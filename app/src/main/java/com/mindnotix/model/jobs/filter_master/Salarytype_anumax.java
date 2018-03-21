package com.mindnotix.model.jobs.filter_master;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Awesome Pojo Generator
 */
public class Salarytype_anumax {
    @SerializedName("job_salary_type")
    @Expose
    private String job_salary_type;
    @SerializedName("job_salary_value")
    @Expose
    private String job_salary_value;
    @SerializedName("job_salary_id")
    @Expose
    private String job_salary_id;
    @SerializedName("job_salary_amount")
    @Expose
    private String job_salary_amount;
    @SerializedName("status")
    @Expose
    private String status;

    public String getJob_salary_type() {
        return job_salary_type;
    }

    public void setJob_salary_type(String job_salary_type) {
        this.job_salary_type = job_salary_type;
    }

    public String getJob_salary_value() {
        return job_salary_value;
    }

    public void setJob_salary_value(String job_salary_value) {
        this.job_salary_value = job_salary_value;
    }

    public String getJob_salary_id() {
        return job_salary_id;
    }

    public void setJob_salary_id(String job_salary_id) {
        this.job_salary_id = job_salary_id;
    }

    public String getJob_salary_amount() {
        return job_salary_amount;
    }

    public void setJob_salary_amount(String job_salary_amount) {
        this.job_salary_amount = job_salary_amount;
    }

    public String getStatus() {
        return status;
    }

        public void setStatus(String status) {
        this.status = status;
    }


    /**
     * Pay attention here, you have to override the toString method as the
     * ArrayAdapter will reads the toString of the given object for the name
     *
     * @return ptg_name
     */
    @Override
    public String toString() {
        return job_salary_amount;
    }
}