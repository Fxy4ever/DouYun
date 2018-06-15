package hundredSister.bean;

/**
 * Created by mac on 2018/5/12.
 */

public class MediaBean {
        /**
         * text :
         配乐加萌犬，怎么可以如此魔性！哈哈哈

         * hate : 8
         * videotime : 0
         * voicetime : 0
         * weixin_url : http://m.budejie.com/detail-27887469.html/
         * profile_image : http://wimg.spriteapp.cn/profile/large/2018/05/11/5af4ec7514f53_mini.jpg
         * width : 0
         * voiceuri :
         * type : 41
         * ct : 2018-05-12 12:40:03.321
         * id : 27887469
         * love : 100
         * height : 0
         * _id : 5af670236e36b5254ce6d37f
         * video_uri : http://mvideo.spriteapp.cn/video/2018/0511/5af48824085f4_wpc.mp4
         * voicelength : 0
         * name : 马东皮
         * create_time : 2018-05-12 12:40:03
         */

        private String text;
        private String hate;
        private String videotime;
        private String voicetime;
        private String weixin_url;
        private String profile_image;
        private String width;
        private String voiceuri;
        private String type;
        private String ct;
        private String id;
        private String love;
        private String height;
        private String _id;
        private String video_uri;
        private String voicelength;
        private String name;
        private String create_time;

    private MediaBean(Builder builder) {
        setText(builder.text);
        setHate(builder.hate);
        setVideotime(builder.videotime);
        setVoicetime(builder.voicetime);
        setWeixin_url(builder.weixin_url);
        setProfile_image(builder.profile_image);
        setWidth(builder.width);
        setVoiceuri(builder.voiceuri);
        setType(builder.type);
        setCt(builder.ct);
        setId(builder.id);
        setLove(builder.love);
        setHeight(builder.height);
        set_id(builder._id);
        setVideo_uri(builder.video_uri);
        setVoicelength(builder.voicelength);
        setName(builder.name);
        setCreate_time(builder.create_time);
    }

    public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getHate() {
            return hate;
        }

        public void setHate(String hate) {
            this.hate = hate;
        }

        public String getVideotime() {
            return videotime;
        }

        public void setVideotime(String videotime) {
            this.videotime = videotime;
        }

        public String getVoicetime() {
            return voicetime;
        }

        public void setVoicetime(String voicetime) {
            this.voicetime = voicetime;
        }

        public String getWeixin_url() {
            return weixin_url;
        }

        public void setWeixin_url(String weixin_url) {
            this.weixin_url = weixin_url;
        }

        public String getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getVoiceuri() {
            return voiceuri;
        }

        public void setVoiceuri(String voiceuri) {
            this.voiceuri = voiceuri;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCt() {
            return ct;
        }

        public void setCt(String ct) {
            this.ct = ct;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLove() {
            return love;
        }

        public void setLove(String love) {
            this.love = love;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getVideo_uri() {
            return video_uri;
        }

        public void setVideo_uri(String video_uri) {
            this.video_uri = video_uri;
        }

        public String getVoicelength() {
            return voicelength;
        }

        public void setVoicelength(String voicelength) {
            this.voicelength = voicelength;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

    public static final class Builder {
        private String text;
        private String hate;
        private String videotime;
        private String voicetime;
        private String weixin_url;
        private String profile_image;
        private String width;
        private String voiceuri;
        private String type;
        private String ct;
        private String id;
        private String love;
        private String height;
        private String _id;
        private String video_uri;
        private String voicelength;
        private String name;
        private String create_time;

        public Builder() {
        }

        public Builder text(String val) {
            text = val;
            return this;
        }

        public Builder hate(String val) {
            hate = val;
            return this;
        }

        public Builder videotime(String val) {
            videotime = val;
            return this;
        }

        public Builder voicetime(String val) {
            voicetime = val;
            return this;
        }

        public Builder weixin_url(String val) {
            weixin_url = val;
            return this;
        }

        public Builder profile_image(String val) {
            profile_image = val;
            return this;
        }

        public Builder width(String val) {
            width = val;
            return this;
        }

        public Builder voiceuri(String val) {
            voiceuri = val;
            return this;
        }

        public Builder type(String val) {
            type = val;
            return this;
        }

        public Builder ct(String val) {
            ct = val;
            return this;
        }

        public Builder id(String val) {
            id = val;
            return this;
        }

        public Builder love(String val) {
            love = val;
            return this;
        }

        public Builder height(String val) {
            height = val;
            return this;
        }

        public Builder _id(String val) {
            _id = val;
            return this;
        }

        public Builder video_uri(String val) {
            video_uri = val;
            return this;
        }

        public Builder voicelength(String val) {
            voicelength = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder create_time(String val) {
            create_time = val;
            return this;
        }

        public MediaBean build() {
            return new MediaBean(this);
        }
    }
}
