package gestionnaire_stock;

import java.util.ArrayList;
import java.util.UUID;

public class Article {
    private UUID id = UUID.randomUUID();
    private String description;
    private Double qty;
    private Double prixAchat;
    private String label;
    private Double prixVente;

    private Category category;                                  // 1
    private ArrayList<Supplier> suppliers = new ArrayList<>();  // *
    private ArrayList<MVT> mvts = new ArrayList<>();            // *

    public Article() {
    }

    public Article(String label, String description, Double qty, Double prixAchat, Double prixVente, Category category) {
        this.label = label;
        this.description = description;
        this.qty = qty;
        this.prixAchat = prixAchat;
        this.prixVente = prixVente;
        setCategory(category);
    }

    // pour recréer un article lu depuis la base
    public Article(UUID id, String label, String description, Double qty, Double prixAchat, Double prixVente, Category category) {
        this(label, description, qty, prixAchat, prixVente, category);
        this.id = id;
    }

    public void setCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Un article doit appartenir à une catégorie");
        }
        this.category = category;
    }
    public Category getCategory() { return category; }

    public void addSupplier(Supplier s) {
        if (!suppliers.contains(s)) suppliers.add(s);
        if (!s.getArticles().contains(this)) s.getArticles().add(this);
    }
    public ArrayList<Supplier> getSuppliers() { return suppliers; }

    // Ajoute un mouvement ET met à jour le stock
    public void addMvt(MVT m) {
        if (m.getType() == MVT.Type.OUTPUT) {
            if (m.getQty() > qty) {
                throw new IllegalStateException("Stock insuffisant");
            }
            qty = qty - m.getQty();
        } else {
            qty = qty + m.getQty();
        }
        mvts.add(m);
    }
    public ArrayList<MVT> getMvts() { return mvts; }

    public UUID getId() { return id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getQty() { return qty; }
    public void setQty(Double qty) { this.qty = qty; }

    public Double getPrixAchat() { return prixAchat; }
    public void setPrixAchat(Double prixAchat) { this.prixAchat = prixAchat; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public Double getPrixVente() { return prixVente; }
    public void setPrixVente(Double prixVente) { this.prixVente = prixVente; }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", qty=" + qty +
                ", prixAchat=" + prixAchat +
                ", prixVente=" + prixVente +
                ", category=" + (category == null ? null : category.getName()) +
                '}';
    }
}