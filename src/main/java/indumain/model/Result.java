package indumain.model;

public class Result {
    private String url;
    private String industry;

    public Result(String url, String industry) {
        this.url = url;
        this.industry = industry;
    }

    public String getUrl() {
        return url;
    }

    public String getIndustry() {
        return industry;
    }
}
