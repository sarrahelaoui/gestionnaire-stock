package gestionnaire_stock;

import java.util.ArrayList;
import java.util.UUID;

public class Supplier {
    private UUID id = UUID.randomUUID();
    private String fullname;
    private String email;
    private String tel;
    private String vat;
    private ArrayList<Article> articles = new ArrayList<>();   // AJOUT : relation *
 
    public Supplier(String fullname, String email, String tel, String vat) {
        this.fullname = fullname;
        this.email = email; 
        this.tel = tel; 
        this.vat = vat;
    }

    // AJOUT : constructeur pour recréer un fournisseur lu depuis la base
    public Supplier(UUID id, String fullname, String email, String tel, String vat) {
        this(fullname, email, tel, vat);
        this.id = id;
    }

    public ArrayList<Article> getArticles() { return articles; }
 
    public UUID getId() { 
        return id; }
    public String getFullname() { 
        return fullname; }
    public void setFullname(String f) {
        this.fullname = f; }
    public String getEmail() { 
        return email; }
    public void setEmail(String e) { 
        this.email = e; }
    public String getTel() { 
        return tel; }
    public void setTel(String t) {
        this.tel = t; }
    public String getVat() { 
        return vat; }
    public void setVat(String v) {
        this.vat = v; }
 
    @Override public String toString() { return "Supplier{" + fullname + "}"; }
}