package com.yc.verbaltalk.chat.bean;

/**
 * Created by sunshey on 2019/5/11.
 */

public class LoveUpDownPhotoBean {


        /**
         * love_id : 1
         * lovewords_id : 1
         * content : 我想去取一下东西
         * ans_sex : 1
         */

        public int id;
        public int lovewords_id;
        public String content;
        public String ans_sex;

        @Override
        public String toString() {
                return "LoveUpDownPhotoBean{" +
                        "love_id=" + id +
                        ", lovewords_id=" + lovewords_id +
                        ", content='" + content + '\'' +
                        ", ans_sex='" + ans_sex + '\'' +
                        '}';
        }
}
