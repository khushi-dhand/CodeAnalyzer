package model;

public class CodeFile {
    private String filename;
    private String language;

    public CodeFile(String filename, String language) {
        this.filename = filename;
        this.language = language;
    }

    public String getFilename() { return filename; }
    public String getLanguage() { return language; }
}
