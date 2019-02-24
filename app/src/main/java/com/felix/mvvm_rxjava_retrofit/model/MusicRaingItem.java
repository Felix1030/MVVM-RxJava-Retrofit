package com.felix.mvvm_rxjava_retrofit.model;

import java.util.List;

public class MusicRaingItem {
    /**
     * pic_s210 : http://business.cdn.qianqian.com/qianqian/pic/bos_client_dea655f4be544132fb0b5899f063d82e.jpg
     * bg_pic : http://business0.qianqian.com/qianqian/file/5c3d586d234b4_292.png
     * color : 0x5B9400
     * pic_s444 : http://hiphotos.qianqian.com/ting/pic/item/78310a55b319ebc4845c84eb8026cffc1e17169f.jpg
     * count : 4
     * type : 1
     * content : [{"all_rate":"96,128,224,320,flac","song_id":"612313835","rank_change":"2","biaoshi":"first,lossless,perm-3","author":"沈腾","album_id":"612313833","pic_small":"http://qukufile2.qianqian.com/data2/pic/841c14e63a26b6d6cdc68400047b9ebe/612314830/612314830.jpg@s_1,w_90,h_90","title":"平凡之路（电影《飞驰人生》特别版）","pic_big":"http://qukufile2.qianqian.com/data2/pic/841c14e63a26b6d6cdc68400047b9ebe/612314830/612314830.jpg@s_1,w_150,h_150","album_title":"平凡之路（电影《飞驰人生》特别版）"},{"all_rate":"96,128,224,320,flac","song_id":"612387139","rank_change":"0","biaoshi":"first,lossless,perm-1","author":"韩寒","album_id":"612387137","pic_small":"http://qukufile2.qianqian.com/data2/pic/cd58b811b510d0b36193056d3b57a2b1/612387156/612387156.jpg@s_1,w_90,h_90","title":"奉献（电影《飞驰人生》片尾曲）","pic_big":"http://qukufile2.qianqian.com/data2/pic/cd58b811b510d0b36193056d3b57a2b1/612387156/612387156.jpg@s_1,w_150,h_150","album_title":"奉献"},{"all_rate":"96,128,224,320,flac","song_id":"612412180","rank_change":"5","biaoshi":"first,lossless,perm-1","author":"张过年","album_id":"612412178","pic_small":"http://qukufile2.qianqian.com/data2/pic/f1279912c620588fa3490d883eb48155/612418993/612418993.jpg@s_1,w_90,h_90","title":"我是真的爱你（电影《飞驰人生》插曲）","pic_big":"http://qukufile2.qianqian.com/data2/pic/f1279912c620588fa3490d883eb48155/612418993/612418993.jpg@s_1,w_150,h_150","album_title":"我是真的爱你（电影《飞驰人生》插曲）"},{"all_rate":"96,128,224,320,flac","song_id":"612368947","rank_change":"-3","biaoshi":"first,lossless,perm-1","author":"周笔畅","album_id":"612368945","pic_small":"http://qukufile2.qianqian.com/data2/pic/8cefae6be35b7bed624b39bfe0f245a7/612369158/612369158.jpg@s_1,w_90,h_90","title":"去流浪（电影《流浪地球》推广曲）","pic_big":"http://qukufile2.qianqian.com/data2/pic/8cefae6be35b7bed624b39bfe0f245a7/612369158/612369158.jpg@s_1,w_150,h_150","album_title":"去流浪（电影《流浪地球》推广曲）"}]
     * bg_color : 0xEFF5E6
     * web_url :
     * name : 新歌榜
     * comment : 该榜单是根据千千音乐平台歌曲每日播放量自动生成的数据榜单，统计范围为近期发行的歌曲，每日更新一次
     * pic_s192 : http://business.cdn.qianqian.com/qianqian/pic/bos_client_9a4fbbbfa50203aaa9e69bf189c6a45b.jpg
     * pic_s260 : http://hiphotos.qianqian.com/ting/pic/item/e850352ac65c1038cb0f3cb0b0119313b07e894b.jpg
     */

    private String pic_s210;
    private String bg_pic;
    private String color;
    private String pic_s444;
    private int count;
    private int type;
    private String bg_color;
    private String web_url;
    private String name;
    private String comment;
    private String pic_s192;
    private String pic_s260;
    private List<ContentBean> content;

    public String getPic_s210() {
        return pic_s210;
    }

    public void setPic_s210(String pic_s210) {
        this.pic_s210 = pic_s210;
    }

    public String getBg_pic() {
        return bg_pic;
    }

    public void setBg_pic(String bg_pic) {
        this.bg_pic = bg_pic;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPic_s444() {
        return pic_s444;
    }

    public void setPic_s444(String pic_s444) {
        this.pic_s444 = pic_s444;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBg_color() {
        return bg_color;
    }

    public void setBg_color(String bg_color) {
        this.bg_color = bg_color;
    }

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPic_s192() {
        return pic_s192;
    }

    public void setPic_s192(String pic_s192) {
        this.pic_s192 = pic_s192;
    }

    public String getPic_s260() {
        return pic_s260;
    }

    public void setPic_s260(String pic_s260) {
        this.pic_s260 = pic_s260;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * all_rate : 96,128,224,320,flac
         * song_id : 612313835
         * rank_change : 2
         * biaoshi : first,lossless,perm-3
         * author : 沈腾
         * album_id : 612313833
         * pic_small : http://qukufile2.qianqian.com/data2/pic/841c14e63a26b6d6cdc68400047b9ebe/612314830/612314830.jpg@s_1,w_90,h_90
         * title : 平凡之路（电影《飞驰人生》特别版）
         * pic_big : http://qukufile2.qianqian.com/data2/pic/841c14e63a26b6d6cdc68400047b9ebe/612314830/612314830.jpg@s_1,w_150,h_150
         * album_title : 平凡之路（电影《飞驰人生》特别版）
         */

        private String all_rate;
        private String song_id;
        private String rank_change;
        private String biaoshi;
        private String author;
        private String album_id;
        private String pic_small;
        private String title;
        private String pic_big;
        private String album_title;

        public String getAll_rate() {
            return all_rate;
        }

        public void setAll_rate(String all_rate) {
            this.all_rate = all_rate;
        }

        public String getSong_id() {
            return song_id;
        }

        public void setSong_id(String song_id) {
            this.song_id = song_id;
        }

        public String getRank_change() {
            return rank_change;
        }

        public void setRank_change(String rank_change) {
            this.rank_change = rank_change;
        }

        public String getBiaoshi() {
            return biaoshi;
        }

        public void setBiaoshi(String biaoshi) {
            this.biaoshi = biaoshi;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAlbum_id() {
            return album_id;
        }

        public void setAlbum_id(String album_id) {
            this.album_id = album_id;
        }

        public String getPic_small() {
            return pic_small;
        }

        public void setPic_small(String pic_small) {
            this.pic_small = pic_small;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic_big() {
            return pic_big;
        }

        public void setPic_big(String pic_big) {
            this.pic_big = pic_big;
        }

        public String getAlbum_title() {
            return album_title;
        }

        public void setAlbum_title(String album_title) {
            this.album_title = album_title;
        }

        @Override
        public String toString() {
            return "ContentBean{" +
                    "all_rate='" + all_rate + '\'' +
                    ", song_id='" + song_id + '\'' +
                    ", rank_change='" + rank_change + '\'' +
                    ", biaoshi='" + biaoshi + '\'' +
                    ", author='" + author + '\'' +
                    ", album_id='" + album_id + '\'' +
                    ", pic_small='" + pic_small + '\'' +
                    ", title='" + title + '\'' +
                    ", pic_big='" + pic_big + '\'' +
                    ", album_title='" + album_title + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MusicRaingItem{" +
                "pic_s210='" + pic_s210 + '\'' +
                ", bg_pic='" + bg_pic + '\'' +
                ", color='" + color + '\'' +
                ", pic_s444='" + pic_s444 + '\'' +
                ", count=" + count +
                ", type=" + type +
                ", bg_color='" + bg_color + '\'' +
                ", web_url='" + web_url + '\'' +
                ", name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                ", pic_s192='" + pic_s192 + '\'' +
                ", pic_s260='" + pic_s260 + '\'' +
                ", content=" + content +
                '}';
    }
}
