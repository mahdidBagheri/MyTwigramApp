package TimeLine.ServerController;

import Connection.Client.ClientRequest;
import Connection.DataBaseConnection.ConnectionToDataBase;
import Connection.Server.ServerConnection;
import TimeLine.Model.TimeLine;
import Twitt.Controller.ServerTwittsController;
import Twitt.Exceptions.TwittReadDataException;
import Twitt.Model.Twitt;
import User.Controller.ServerUserController;
import User.Exceptions.unsuccessfullReadDataFromDatabase;
import User.Model.Activity;
import User.Model.User;
import Utils.DateTime;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

public class ServerTimeLineController {
    ServerConnection serverConnection;
    TimeLine timeLine;

    public ServerTimeLineController(ServerConnection serverConnection, TimeLine timeLine) {
        this.serverConnection = serverConnection;
        this.timeLine = timeLine;
    }


    public void findTimeLineTwitts(ClientRequest clientRequest) throws unsuccessfullReadDataFromDatabase, SQLException, TwittReadDataException {
        User user = new User();
        ServerUserController userController = new ServerUserController(user);
        userController.readAll(clientRequest.getUsername());

        LinkedList<Activity> Activities;
        timeLine.clearTimeLine();


        if(!user.getFollowing().isEmpty()){
            for(int i = 0; i < user.getFollowing().size(); i++) {
                if(timeLine.getTwitts().size() > 20){
                    break;
                }

                User followingUserObj = user.getFollowing().get(i);
                ServerUserController followingUserController = new ServerUserController(user.getFollowing().get(i));
                ServerUserController mainUserController = new ServerUserController(user);

                mainUserController.readMutedUsers();

                if(user.isMuted(followingUserObj.getUserUUID())){
                    continue;
                }

                followingUserController.readActivities();
                Activities = followingUserObj.getActivities();

                for (Activity activity :Activities){
                    if(activity.getType().equals("Like")){
                        Twitt likedTwitts = new Twitt();
                        likedTwitts.setUserWhoLiked(followingUserObj);
                        likedTwitts.setTwittUUID(activity.getUUID());
                        ServerTwittsController twittsController = new ServerTwittsController(likedTwitts);
                        twittsController.readDataFromDataBaseByUUID();
                        if(likedTwitts.getText().equals("Deleted Twitt!")){
                            continue;
                        }

                        addToTimeLine(likedTwitts);
                        continue;
                    }
                    else if(activity.getType().equals("Twitt")){
                        Twitt twitt = new Twitt();
                        twitt.setTwittUUID(activity.getUUID());
                        ServerTwittsController twittsController = new ServerTwittsController(twitt);
                        twittsController.readDataFromDataBaseByUUID();
                        if(twitt.getText().equals("Deleted Twitt!")){
                            continue;
                        }
                        addToTimeLine(twitt);
                        continue;
                    }
                    else if(activity.getType().equals("ReTwitt")){
                        Twitt reTwitt = new Twitt();
                        reTwitt.setTwittUUID(activity.getUUID());
                        ServerTwittsController twittsController = new ServerTwittsController(reTwitt);
                        twittsController.readDataFromDataBaseByUUID();
                        twittsController.readParentRetwitt();
                        if(reTwitt.getText().equals("Deleted Twitt!")){
                            continue;
                        }
                        addToTimeLine(reTwitt);
                        continue;
                    }
                    else if(activity.getType().equals("Reply")){
                        Twitt reply  = new Twitt();
                        reply.setTwittUUID(activity.getUUID());
                        ServerTwittsController twittsController = new ServerTwittsController(reply);
                        twittsController.readDataFromDataBaseByUUID();
                        twittsController.readParentReply();

                        if(reply.getText().equals("Deleted Twitt!")){
                            continue;
                        }
                        addToTimeLine(reply);
                        continue;
                    }

                }
            }
        }
        if(timeLine.getTwitts().size() < 10){
            LinkedList<String> FavouriteTwitts = findFavouriteTwitts();
            for(int ii = 0; ii<FavouriteTwitts.size(); ii++){
                Twitt twitt = new Twitt();
                twitt.setTwittUUID(FavouriteTwitts.get(ii));
                ServerTwittsController twittsController = new ServerTwittsController(twitt);
                twittsController.readDataFromDataBaseByUUID();
                addToTimeLine(twitt);
            }
        }

    }

    public void addToTimeLine(Twitt twitt){
        timeLine.getTwitts().add(twitt);
    }

    public LinkedList<String> findFavouriteTwitts() throws SQLException, TwittReadDataException {
        ConnectionToDataBase connectionToDataBase = new ConnectionToDataBase();
        DateTime dateTime = new DateTime();
        String sql = String.format("SELECT * FROM \"TwittsTable\" where \"Type\" = 'Twitt' AND \"Date\" between '%s' AND '%s';", dateTime.Yesterday(), dateTime.Now());
        ResultSet rs = connectionToDataBase.executeQuery(sql);
        connectionToDataBase.Disconect();
        HashMap<String, Integer> twittsMap = new HashMap<String, Integer>();
        int counter = 0;
        if (rs != null) {
            while (rs.next() && counter < 50) {
                Twitt twitt = new Twitt();
                twitt.setTwittUUID(rs.getString(1));
                ServerTwittsController twittsController = new ServerTwittsController(twitt);
                twittsController.readLikes();
                if (twittsMap.size() < 20) {
                    twittsMap.put(rs.getString(1), twitt.getLikes().size());
                } else  {
                    twittsMap.put(rs.getString(1), twitt.getLikes().size());
                    twittsMap = removeSmallest(twittsMap);
                }
                counter++;

            }
        }
        LinkedList<String> valueList = new LinkedList<String>(twittsMap.keySet());
        connectionToDataBase.Disconect();
        return valueList;
    }

    public HashMap<String,Integer > removeSmallest(HashMap<String, Integer> map) {
        Set<String> keys = map.keySet();
        TreeSet<String> smallest = new TreeSet<String>() {
            public int compare(String o1, String o2) {
                return map.get(o1) - map.get(o2);
            }
        };
        smallest.addAll(keys);
        keys.remove(smallest.pollFirst());
        return map;
    }

}
