package kirchnerei.httpclient;

import java.io.PrintWriter;
import java.io.StringWriter;

public class HttpResponseError implements HttpResponse {

    private final Throwable error;

    private final int statusCode;

    private final long duration;

    HttpResponseError(Throwable error, int statusCode, long duration) {
        this.error = error;
        this.statusCode = statusCode;
        this.duration = duration;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public Throwable getError() {
        return error;
    }

    @Override
    public String getContent() {
        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        this.error.printStackTrace(out);
        IOUtils.close(writer);
        return writer.toString();
    }

    @Override
    public long getDuration() {
        return duration;
    }

    @Override
    public boolean hasError() {
        return true;
    }
}
