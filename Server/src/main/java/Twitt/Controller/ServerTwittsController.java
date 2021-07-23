package Twitt.Controller;

import Connection.DataBaseConnection.ConnectionToDataBase;
import Connection.Utils.DataBaseUtils;
import Twitt.Exceptions.AlreadyReportedException;
import Twitt.Exceptions.TwittReadDataException;
import Twitt.Model.Twitt;
import User.Controller.ServerUserController;
import User.Exceptions.unsuccessfullReadDataFromDatabase;
import User.Model.User;
import Utils.DateTime;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import ServerConstants.ServerConstants;

public class ServerTwittsController {
    Twitt twitt;
    ConnectionToDataBase connectionToDataBase;

    public ServerTwittsController(Twitt twitt) {
        this.twitt = twitt;
        this.connectionToDataBase = new ConnectionToDataBase();
    }

    public void readLikes() throws SQLException, TwittReadDataException {
        LinkedList<String> twittLikesList = new LinkedList<>();
        if (!DataBaseUtils.isEmptyTable("Twitt" + twitt.getTwittUUID() + "Likes")) {
            String sql = String.format("select * from \"Twitt%sLikes\";", twitt.getTwittUUID());
            ResultSet rs = connectionToDataBase.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    twittLikesList.add(rs.getString(2));
                }
                twitt.setLikes(twittLikesList);
            }
            else {
                throw new TwittReadDataException("Could not find likes");
            }
        }
    }

    public void readReTwitts() throws SQLException, TwittReadDataException, unsuccessfullReadDataFromDatabase {
        LinkedList<String> twittReTwittsList = new LinkedList<>();
        LinkedList<User> twittReTwittersList = new LinkedList<>();

        if (!DataBaseUtils.isEmptyTable("Twitt" + twitt.getTwittUUID() + "Retwitts")) {
            String sql = String.format("select * from \"Twitt%sRetwitts\";", twitt.getTwittUUID());
            ResultSet rs = connectionToDataBase.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    twittReTwittsList.add(rs.getString(2));
                    Twitt chileTwitt = new Twitt();
                    chileTwitt.setTwittUUID(rs.getString(2));
                    ServerTwittsController serverTwittsController = new ServerTwittsController(chileTwitt);
                    serverTwittsController.readAuthor();
                    twittReTwittersList.add(chileTwitt.getAuthor());

                }
                twitt.setReTwitts(twittReTwittsList);
                twitt.setTwittReTwittersList(twittReTwittersList);
            }
            else {
                throw new TwittReadDataException("could not load retwitts");
            }
        }
    }

    public void readReplies() throws SQLException, TwittReadDataException, unsuccessfullReadDataFromDatabase {
        LinkedList<Twitt> twittRepliesList = new LinkedList<>();

        if (!DataBaseUtils.isEmptyTable("Twitt" + twitt.getTwittUUID() + "Replies")) {
            String sql = String.format("select * from \"Twitt%sReplies\";", twitt.getTwittUUID());
            ResultSet rs = connectionToDataBase.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    Twitt reply = new Twitt();
                    reply.setTwittUUID(rs.getString(2));
                    ServerTwittsController replyController = new ServerTwittsController(reply);
                    replyController.readAuthor();
                    replyController.readText();
                    twittRepliesList.add(reply);
                }
                twitt.setReplies(twittRepliesList);
            }
            else {
                throw new TwittReadDataException("could not load Replies");
            }
        }

    }

    public void readReports() throws SQLException, TwittReadDataException {
        LinkedList<String> twittReportList = new LinkedList<>();

        String sql = String.format("select \"Username\" from \"Twitt%sReports\"; ",twitt.getTwittUUID() );

        ResultSet rs = connectionToDataBase.executeQuery(sql);
        if (!DataBaseUtils.isEmptyTable("Twitt" + twitt.getTwittUUID() + "Reports")) {
            if (rs != null) {
                while (rs.next()) {
                    twittReportList.add(rs.getString(1));
                }
                twitt.setReports(twittReportList);
            } else {
                throw new TwittReadDataException("could not load reports");
            }
        }

    }

    public void report() throws SQLException, FileNotFoundException, AlreadyReportedException, TwittReadDataException {
        DateTime dateTime = new DateTime();

        User mainUser = new User();
        ServerUserController userController = new ServerUserController(mainUser);
        //userController.setAsMainUser();
        readReports();
        if(!twitt.getReports().contains(mainUser.getUserUUID())) {
            String sql = String.format("insert into \"Twitt%sReports\" (\"UUID\",\"UserUUIDs\",\"Date\") values(uuid_generate_v4(),'%s','%s'); ", twitt.getTwittUUID(), mainUser.getUserUUID(),dateTime.Now() );
            connectionToDataBase.executeUpdate(sql);
        }
        else{
            throw new AlreadyReportedException("You Already Reported this twitt");
        }
        //new Logger("INFO", String.format("User %s reported twitt %s",mainUser.getUserUUID(), twitt.getTwittUUID()));
    }

    public void save() throws SQLException, FileNotFoundException, TwittReadDataException, unsuccessfullReadDataFromDatabase {
        DateTime dateTime = new DateTime();
        User mainUser = new User();
        ServerUserController userController = new ServerUserController(mainUser);
        ///userController.setAsMainUser();
        userController.readSavedTwitts();

        if (!mainUser.getSavedTwitts().contains(twitt.getTwittUUID())) {
            String sql = String.format("insert into \"User%sSavedTwitts\" (\"UUID\",\"TwittUUIDs\",\"Date\") values(uuid_generate_v4(),'%s','%s'); ", mainUser.getUserUUID(), twitt.getTwittUUID(), dateTime.Now());
            connectionToDataBase.executeUpdate(sql);
        } else {
            throw new unsuccessfullReadDataFromDatabase("Could not Save Twitt");
        }
    }

    public void readDataFromDataBaseByUUID() throws SQLException, TwittReadDataException, unsuccessfullReadDataFromDatabase {

        readReports();
        readLikes();
        readReplies();
        readReTwitts();
        readAuthor();
        readPicAddress();

        String sql = String.format("select * from  \"TwittsTable\"  where  \"TwittUUID\" = '%s'", twitt.getTwittUUID());
        ResultSet rs = connectionToDataBase.executeQuery(sql);
        connectionToDataBase.Disconect();

        if (rs != null) {
            if (rs.next()) {
                twitt.setText(rs.getString(2));
                twitt.setAuthorUUID(rs.getString(3));
                twitt.setDate(rs.getString(8));
                twitt.setType(rs.getString(13));
            }
            else {
                throw new TwittReadDataException("Could not load twitt data");
            }
        }
        else {
            throw new TwittReadDataException("no such twitt found");
        }
    }

    public void readAuthor() throws TwittReadDataException, SQLException, unsuccessfullReadDataFromDatabase {
        User user = new User();

        ServerTwittsController twittsController = new ServerTwittsController(this.twitt);
        twittsController.readAuthorUUID();
        user.setUserUUID(twitt.getAuthorUUID());
        ServerUserController serverUserController = new ServerUserController(user);
        serverUserController.readAll();
        twitt.setAuthor(user);
    }

    public void readAuthorUUID() throws SQLException, unsuccessfullReadDataFromDatabase {
        String sql = String.format("select \"Author\" from \"TwittsTable\" where \"TwittUUID\" = '%s' ;", twitt.getTwittUUID());
        ResultSet rs = connectionToDataBase.executeQuery(sql);
        if(rs != null && rs.next()){
            twitt.setAuthorUUID(rs.getString(1));
        }
        else {
            throw new unsuccessfullReadDataFromDatabase("could not retrive author uuid");
        }
    }

    public void readText() throws SQLException, TwittReadDataException {
        String sql = String.format("select \"text\" from \"TwittsTable\" where \"TwittUUID\" = '%s' ;", twitt.getTwittUUID());
        ResultSet rs = connectionToDataBase.executeQuery(sql);
        if(rs != null && rs.next()){
            twitt.setText(rs.getString(1));
        }
        else {
            throw new TwittReadDataException("could not retrive author uuid");
        }
    }

    public void readDate() throws SQLException, TwittReadDataException {
        String sql = String.format("select \"Date\" from \"TwittsTable\" where \"TwittUUID\" = '%s' ;", twitt.getTwittUUID());
        ResultSet rs = connectionToDataBase.executeQuery(sql);
        if(rs != null && rs.next()){
            twitt.setText(rs.getDate(1).toString() + " " + rs.getTime(1).toString());
        }
        else {
            throw new TwittReadDataException("could not retrive author uuid");
        }
    }

    public void readParentReply() throws SQLException, TwittReadDataException {
        String sql = String.format("select \"ParentReply\" from \"TwittsTable\" where \"TwittUUID\" = '%s' ;", twitt.getTwittUUID());
        ResultSet rs = connectionToDataBase.executeQuery(sql);
        if(rs != null && rs.next()){
            twitt.setParent(rs.getString(1));
        }
        else {
            throw new TwittReadDataException("could not retrive author uuid");
        }
    }

    public void readParentRetwitt() throws SQLException, TwittReadDataException {
        String sql = String.format("select \"ParentRetwitt\" from \"TwittsTable\" where \"TwittUUID\" = '%s' ;", twitt.getTwittUUID());
        ResultSet rs = connectionToDataBase.executeQuery(sql);
        if(rs != null && rs.next()){
            twitt.setParent(rs.getString(1));
        }
        else {
            throw new TwittReadDataException("could not retrive author uuid");
        }
    }

    public void readType() throws SQLException, TwittReadDataException {
        String sql = String.format("select \"Type\" from \"TwittsTable\" where \"TwittUUID\" = '%s' ;", twitt.getTwittUUID());
        ResultSet rs = connectionToDataBase.executeQuery(sql);
        if(rs != null && rs.next()){
            twitt.setType(rs.getString(1));
        }
        else {
            throw new TwittReadDataException("could not retrive author uuid");
        }
    }

    public void readHasPic() throws SQLException, TwittReadDataException {
        String sql = String.format("select \"hasPic\" from \"TwittsTable\" where \"TwittUUID\" = '%s' ;", twitt.getTwittUUID());
        ResultSet rs = connectionToDataBase.executeQuery(sql);
        if(rs != null && rs.next()){
            twitt.setHasPic(rs.getString(1));
        }
        else {
            throw new TwittReadDataException("could not retrive author uuid");
        }
    }

    public void readPicAddress() throws SQLException, TwittReadDataException {
        String sql = String.format("select \"PicAddress\" from \"TwittsTable\" where \"TwittUUID\" = '%s' ;", twitt.getTwittUUID());
        ResultSet rs = connectionToDataBase.executeQuery(sql);
        if(rs != null && rs.next()){
            twitt.setPicAddress(rs.getString(1));
            String path = rs.getString(1);
            if(path != null) {
                ImageIcon myImage = new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(ServerConstants.picWidth, ServerConstants.picHeight, Image.SCALE_DEFAULT));
                twitt.setPic(myImage);
                twitt.setPicFile(new File(path));
            }
        }
        else {
            throw new TwittReadDataException("could not retrive author uuid");
        }
    }

    public void deleteTwitt() throws SQLException {
        String sql = String.format("update \"TwittsTable\" set \"text\" = '%s' where \"TwittUUID\" = '%s';","Deleted Twitt!",twitt.getTwittUUID());
        connectionToDataBase.executeUpdate(sql);

        String sql1 = String.format("update \"TwittsTable\" set \"PicAddress\" = 'null' where \"TwittUUID\" = '%s';",twitt.getTwittUUID());
        connectionToDataBase.executeUpdate(sql1);

    }



    public void finalize() throws Throwable {
        connectionToDataBase.Disconect();
        super.finalize();
    }

}
