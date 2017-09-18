package com.peng.mantis.model.http.entity;

import java.util.List;

/**
 * Created by pyt on 2017/7/5.
 */

public class HomeMessage {


    /**
     * Header : {"context":{"_jsns":"urn:zimbra"}}
     * Body : {"Response":{"msgs":[{"title":"请假申请 ","content":"邓志林发来一条请假申请","addtime":"2017-06-16 18:01:28","type":34,"ext_id":"QJ1497607288335","msglevel":0},{"title":"请假申请 ","content":"刘少轩发来一条请假申请","addtime":"2017-06-14 20:26:42","type":34,"ext_id":"QJ1497443202552","msglevel":0},{"title":"请假申请 ","content":"刘少轩发来一条请假申请","addtime":"2017-06-14 19:43:12","type":34,"ext_id":"QJ1497440592254","msglevel":0},{"title":"请假申请 ","content":"刘少轩发来一条请假申请","addtime":"2017-06-14 19:23:51","type":34,"ext_id":"QJ1497439431651","msglevel":0},{"title":"请假申请 ","content":"王伟东发来一条请假申请","addtime":"2017-06-14 19:07:14","type":34,"ext_id":"QJ1497438434884","msglevel":0},{"title":"为飞虎队研究会开展活动提供义务服务","content":"云南飞虎保安服务有限公司是经过云南省公安厅批准，由云南省飞虎队研究会创办的保安服务企业，为客户提供门","addtime":"2017-05-05 21:48:05","type":31,"ext_id":"1904","msglevel":0},{"title":"热烈庆祝我公司签约优速物流有限公司","content":"维护仓库正常秩序，进出货物详细登记，预防犯罪","addtime":"2017-05-05 21:44:35","type":31,"ext_id":"1903","msglevel":0},{"title":"中国保安业的发展前景","content":" ❶☞我国目前保安业务所面临的问题:    ①我国目前的保安业就发展来看存在着较大的不平衡现象，就数","addtime":"2017-05-05 21:40:47","type":31,"ext_id":"1902","msglevel":0},{"title":"公安部关于保安技防服务管埋有关问题的批复","content":"云南省公安厅:你厅《关于提供技防服务业务的企业是否应纳入保安监管等有关问题的请示》(辽公通〔2o1","addtime":"2017-05-05 21:08:15","type":31,"ext_id":"1883","msglevel":0},{"title":"公安部关于加强保安服务公司管理的通知","content":"公安部 发布文号:89公治字1号 各省、自治区、直辖市公安厅、局：　　经国务院批准的公安部《关于组","addtime":"2017-05-05 21:05:21","type":31,"ext_id":"1882","msglevel":0},{"title":"警情任务通知","content":"[警情任务]昆明市公安局五华分局月牙塘派出所下发一条紧急任务，请尽快查看并协助处理。","addtime":"2017-05-03 17:47:11","type":36,"ext_id":"442","msglevel":2}],"result":"+OK","_jsns":"urn:benguoCalla"}}
     * _jsns : urn:zimbraSoap
     */
    private HeaderBean Header;
    private BodyBean Body;
    private String _jsns;

    public HeaderBean getHeader() {
        return Header;
    }

    public void setHeader(HeaderBean Header) {
        this.Header = Header;
    }

    public BodyBean getBody() {
        return Body;
    }

    public void setBody(BodyBean Body) {
        this.Body = Body;
    }

    public String get_jsns() {
        return _jsns;
    }

    public void set_jsns(String _jsns) {
        this._jsns = _jsns;
    }

    public static class HeaderBean {
        /**
         * context : {"_jsns":"urn:zimbra"}
         */

        private ContextBean context;

        public ContextBean getContext() {
            return context;
        }

        public void setContext(ContextBean context) {
            this.context = context;
        }

        public static class ContextBean {
            /**
             * _jsns : urn:zimbra
             */

            private String _jsns;

            public String get_jsns() {
                return _jsns;
            }

            public void set_jsns(String _jsns) {
                this._jsns = _jsns;
            }
        }
    }

    public static class BodyBean {
        /**
         * Response : {"msgs":[{"title":"请假申请 ","content":"邓志林发来一条请假申请","addtime":"2017-06-16 18:01:28","type":34,"ext_id":"QJ1497607288335","msglevel":0},{"title":"请假申请 ","content":"刘少轩发来一条请假申请","addtime":"2017-06-14 20:26:42","type":34,"ext_id":"QJ1497443202552","msglevel":0},{"title":"请假申请 ","content":"刘少轩发来一条请假申请","addtime":"2017-06-14 19:43:12","type":34,"ext_id":"QJ1497440592254","msglevel":0},{"title":"请假申请 ","content":"刘少轩发来一条请假申请","addtime":"2017-06-14 19:23:51","type":34,"ext_id":"QJ1497439431651","msglevel":0},{"title":"请假申请 ","content":"王伟东发来一条请假申请","addtime":"2017-06-14 19:07:14","type":34,"ext_id":"QJ1497438434884","msglevel":0},{"title":"为飞虎队研究会开展活动提供义务服务","content":"云南飞虎保安服务有限公司是经过云南省公安厅批准，由云南省飞虎队研究会创办的保安服务企业，为客户提供门","addtime":"2017-05-05 21:48:05","type":31,"ext_id":"1904","msglevel":0},{"title":"热烈庆祝我公司签约优速物流有限公司","content":"维护仓库正常秩序，进出货物详细登记，预防犯罪","addtime":"2017-05-05 21:44:35","type":31,"ext_id":"1903","msglevel":0},{"title":"中国保安业的发展前景","content":" ❶☞我国目前保安业务所面临的问题:    ①我国目前的保安业就发展来看存在着较大的不平衡现象，就数","addtime":"2017-05-05 21:40:47","type":31,"ext_id":"1902","msglevel":0},{"title":"公安部关于保安技防服务管埋有关问题的批复","content":"云南省公安厅:你厅《关于提供技防服务业务的企业是否应纳入保安监管等有关问题的请示》(辽公通〔2o1","addtime":"2017-05-05 21:08:15","type":31,"ext_id":"1883","msglevel":0},{"title":"公安部关于加强保安服务公司管理的通知","content":"公安部 发布文号:89公治字1号 各省、自治区、直辖市公安厅、局：　　经国务院批准的公安部《关于组","addtime":"2017-05-05 21:05:21","type":31,"ext_id":"1882","msglevel":0},{"title":"警情任务通知","content":"[警情任务]昆明市公安局五华分局月牙塘派出所下发一条紧急任务，请尽快查看并协助处理。","addtime":"2017-05-03 17:47:11","type":36,"ext_id":"442","msglevel":2}],"result":"+OK","_jsns":"urn:benguoCalla"}
         */

        private ResponseBean Response;

        public ResponseBean getResponse() {
            return Response;
        }

        public void setResponse(ResponseBean Response) {
            this.Response = Response;
        }

        public static class ResponseBean {
            /**
             * msgs : [{"title":"请假申请 ","content":"邓志林发来一条请假申请","addtime":"2017-06-16 18:01:28","type":34,"ext_id":"QJ1497607288335","msglevel":0},{"title":"请假申请 ","content":"刘少轩发来一条请假申请","addtime":"2017-06-14 20:26:42","type":34,"ext_id":"QJ1497443202552","msglevel":0},{"title":"请假申请 ","content":"刘少轩发来一条请假申请","addtime":"2017-06-14 19:43:12","type":34,"ext_id":"QJ1497440592254","msglevel":0},{"title":"请假申请 ","content":"刘少轩发来一条请假申请","addtime":"2017-06-14 19:23:51","type":34,"ext_id":"QJ1497439431651","msglevel":0},{"title":"请假申请 ","content":"王伟东发来一条请假申请","addtime":"2017-06-14 19:07:14","type":34,"ext_id":"QJ1497438434884","msglevel":0},{"title":"为飞虎队研究会开展活动提供义务服务","content":"云南飞虎保安服务有限公司是经过云南省公安厅批准，由云南省飞虎队研究会创办的保安服务企业，为客户提供门","addtime":"2017-05-05 21:48:05","type":31,"ext_id":"1904","msglevel":0},{"title":"热烈庆祝我公司签约优速物流有限公司","content":"维护仓库正常秩序，进出货物详细登记，预防犯罪","addtime":"2017-05-05 21:44:35","type":31,"ext_id":"1903","msglevel":0},{"title":"中国保安业的发展前景","content":" ❶☞我国目前保安业务所面临的问题:    ①我国目前的保安业就发展来看存在着较大的不平衡现象，就数","addtime":"2017-05-05 21:40:47","type":31,"ext_id":"1902","msglevel":0},{"title":"公安部关于保安技防服务管埋有关问题的批复","content":"云南省公安厅:你厅《关于提供技防服务业务的企业是否应纳入保安监管等有关问题的请示》(辽公通〔2o1","addtime":"2017-05-05 21:08:15","type":31,"ext_id":"1883","msglevel":0},{"title":"公安部关于加强保安服务公司管理的通知","content":"公安部 发布文号:89公治字1号 各省、自治区、直辖市公安厅、局：　　经国务院批准的公安部《关于组","addtime":"2017-05-05 21:05:21","type":31,"ext_id":"1882","msglevel":0},{"title":"警情任务通知","content":"[警情任务]昆明市公安局五华分局月牙塘派出所下发一条紧急任务，请尽快查看并协助处理。","addtime":"2017-05-03 17:47:11","type":36,"ext_id":"442","msglevel":2}]
             * result : +OK
             * _jsns : urn:benguoCalla
             */

            private String result;
            private String _jsns;
            private List<MsgsBean> msgs;

            public String getResult() {
                return result;
            }

            public void setResult(String result) {
                this.result = result;
            }

            public String get_jsns() {
                return _jsns;
            }

            public void set_jsns(String _jsns) {
                this._jsns = _jsns;
            }

            public List<MsgsBean> getMsgs() {
                return msgs;
            }

            public void setMsgs(List<MsgsBean> msgs) {
                this.msgs = msgs;
            }

            public static class MsgsBean {
                /**
                 * title : 请假申请
                 * content : 邓志林发来一条请假申请
                 * addtime : 2017-06-16 18:01:28
                 * type : 34
                 * ext_id : QJ1497607288335
                 * msglevel : 0
                 */

                private String title;
                private String content;
                private String addtime;
                private int type;
                private String ext_id;
                private int msglevel;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getAddtime() {
                    return addtime;
                }

                public void setAddtime(String addtime) {
                    this.addtime = addtime;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getExt_id() {
                    return ext_id;
                }

                public void setExt_id(String ext_id) {
                    this.ext_id = ext_id;
                }

                public int getMsglevel() {
                    return msglevel;
                }

                public void setMsglevel(int msglevel) {
                    this.msglevel = msglevel;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "HomeMessage{" +
                "Header=" + Header +
                ", Body=" + Body +
                ", _jsns='" + _jsns + '\'' +
                '}';
    }
}
