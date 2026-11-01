package gestionnaire_stock;

import java.util.ArrayList;
import java.util.UUID;
        
public class Category {
    private UUID id = UUID.randomUUID();
    private String name;       // unique (contrainte UNIQUE dans la base)
    private String description;
    private Category parent;                                  // 0..1  (appartenir)
    private ArrayList<Category> children = new ArrayList<>(); // 0..*

    public Category(String name, String description) {
        this.name = name; 
        this.description = description;
    }

    // AJOUT : constructeur pour recréer une catégorie lue depuis la base
    public Category(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // AJOUT : relation parent / sous-catégories
    public void addChild(Category c) {
        c.parent = this;
        children.add(c);
    }
    public Category getParent() { return parent; }
    public void setParent(Category parent) { this.parent = parent; }
    public ArrayList<Category> getChildren() { return children; }
    
    public UUID getId() {
        return id; }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() { 
        return description; 
    }
    public void setDescription(String d) {
        this.description = d;
    }

    @Override public String toString() { return "Category{" + name + "}"; }
}