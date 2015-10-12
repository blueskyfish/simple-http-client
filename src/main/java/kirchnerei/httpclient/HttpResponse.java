package kirchnerei.httpclient;

public interface HttpResponse {
    int getStatusCode();

    String getContent();

    long getDuration();

    boolean hasError();

    Throwable getError();
}
