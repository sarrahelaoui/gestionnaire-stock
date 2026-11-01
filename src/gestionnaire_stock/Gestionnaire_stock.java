package gestionnaire_stock;

import DAOController.ArticleDAO;
import DAOController.CategoryDAO;
import DAOController.MvtDAO;
import DAOController.SupplierDAO;

public class Gestionnaire_stock {

    public static void main(String[] args) {
        CategoryDAO categoryDAO = new CategoryDAO();
        SupplierDAO supplierDAO = new SupplierDAO();
        ArticleDAO  articleDAO  = new ArticleDAO();
        MvtDAO      mvtDAO      = new MvtDAO();

        // 1) Catégories (le parent d'abord)
        Category info = new Category("Informatique", "Matériel informatique");
        Category pc   = new Category("PC", "Ordinateurs");
        info.addChild(pc);
        categoryDAO.insert(info);
        categoryDAO.insert(pc);

        // 2) Fournisseur
        Supplier s = new Supplier("Ali Ben Salah", "ali@mail.tn", "71000000", "TN123");
        supplierDAO.insert(s);

        // 3) Article (avec catégorie + fournisseur)
        Article a = new Article("Laptop", "15 pouces", 10.0, 1500.0, 1800.0, pc);
        a.addSupplier(s);
        articleDAO.insert(a);

        // 4) Mouvements (met à jour le stock)
        MVT in = new MVT(a, MVT.Type.INPUT, 5, "Réapprovisionnement");
        a.addMvt(in);
        mvtDAO.insert(in);

        MVT out = new MVT(a, MVT.Type.OUTPUT, 3, "Vente");
        a.addMvt(out);
        mvtDAO.insert(out);

        articleDAO.update(a, a.getId());   // sauvegarde la nouvelle quantité

        // 5) Affichage
        System.out.println("---- Articles ----");
        for (Article x : articleDAO.select()) {
            System.out.println(x + " | fournisseurs: " + x.getSuppliers());
        }
        System.out.println("---- Mouvements ----");
        for (MVT m : mvtDAO.select()) {
            System.out.println(m);
        }
    }
}