package model;

import java.io.Serializable;

public class CompositeProduct implements Serializable {
    String title;
    String composite;

    public CompositeProduct(String title, String composite) {
        super();
        this.title = title;
        this.composite = composite;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComposite() {
        return composite;
    }

    public void setComposite(String composite) {
        this.composite = composite;
    }

    @Override
    public String toString(){
        return title + "   " + composite;
    }
}
