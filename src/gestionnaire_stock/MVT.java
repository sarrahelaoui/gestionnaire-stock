package gestionnaire_stock;   // AJOUT : package manquant

import java.time.LocalDateTime;
import java.util.UUID;
 
public class MVT {
    public enum Type { INPUT, OUTPUT }

    private UUID id = UUID.randomUUID();
    private String dateTime = LocalDateTime.now().toString();
    private String note;
    private double qty;
    private Type type;    

    private Article article;
 
    public MVT(Article article, Type type, double qty, String note) {
        this.article = article;
        this.qty = qty;
        this.type = type;
        this.note = note;
    }

    // AJOUT : constructeur pour recréer un mouvement lu depuis la base
    public MVT(UUID id, Article article, Type type, double qty, String note, String dateTime) {
        this(article, type, qty, note);
        this.id = id;
        this.dateTime = dateTime;
    }
 
    public UUID getId() {
        return id; }
    public Type getType() { 
        return type; }
    public void setType(Type type) {
        this.type = type; }
    public String getDateTime() { 
        return dateTime; }
    public void setDateTime(String d) { 
        this.dateTime = d; }
    public String getNote() {
        return note; }
    public void setNote(String n) { 
        this.note = n; }
    public double getQty() { 
        return qty; }
    public void setQty(double q) { 
        this.qty = q; }
    public Article getArticle() {
        return article; }
 
    @Override public String toString() { return "Mvt{" + type + " " + qty + " @ " + dateTime + "}"; }
}