package ch01.weibo;

import weibo4j.Timeline;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;

import java.util.List;

public class GetHomeTimeline {

    public static void main(String[] args) {
        String access_token = args[0];
        Timeline tm = new Timeline(access_token);
        try {
            StatusWapper statusWrapper = tm.getHomeTimeline();
            Log.logInfo(statusWrapper.toString());
            // test only
            List<Status> statuses = statusWrapper.getStatuses();
            for (int i = 0; i < statuses.size(); i++) {
                int j = statuses.size() - 1 - i;
                Status status = statuses.get(j);
                System.out.println("第" + (j + 1) + "条微博内容是：");
                System.out.println(
                        status.getUser().getScreenName() + " 于 " + status.getCreatedAt() + " 发表了：\n" + status.getText() + "\n");
            }
        } catch (WeiboException e) {
            e.printStackTrace();
        }
    }
}
