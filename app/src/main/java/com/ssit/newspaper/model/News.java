package com.ssit.newspaper.model;

public class News {
        public String n_id;
        public String sl_no;
        public String n_type;
        public String en_name;
        public String bn_name;
        public String n_image;
        public String link;

    public News(String n_id, String sl_no, String n_type, String en_name, String bn_name, String n_image, String link) {
        this.n_id = n_id;
        this.sl_no = sl_no;
        this.n_type = n_type;
        this.en_name = en_name;
        this.bn_name = bn_name;
        this.n_image = n_image;
        this.link = link;
    }

    public String getN_id() {
        return n_id;
    }

    public String getSl_no() {
        return sl_no;
    }

    public String getN_type() {
        return n_type;
    }

    public String getEn_name() {
        return en_name;
    }

    public String getBn_name() {
        return bn_name;
    }

    public String getN_image() {
        return n_image;
    }

    public String getLink() {
        return link;
    }
}
