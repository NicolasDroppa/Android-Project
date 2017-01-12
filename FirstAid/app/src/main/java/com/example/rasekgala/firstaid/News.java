package com.example.rasekgala.firstaid;

import java.io.Serializable;

/**
 * Created by Rasekgala on 2016/10/31.
 */
public class News  implements Serializable{
    private long newsId;
    private String title;
    private String body;
    private String date;
    private String reporter;
    private String status;

    public News() {
    }

    public News(long newsId, String body, String title, String reporter, String date, String status) {
        this.newsId = newsId;
        this.title = title;
        this.body = body;
        this.reporter = reporter;
        this.date = date;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public long getNewsId() {
        return newsId;
    }

    public void setNewsId(long newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
