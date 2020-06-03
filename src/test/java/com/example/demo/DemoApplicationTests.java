package com.example.demo;

//import com.alibaba.fastjson.JSON;
//import com.example.demo.mapper.UserMapper;
//import com.example.demo.mapper.InOutMapper;
//import com.example.demo.mapper.MessageMapper;
//import com.example.demo.mapper.UserMapper;
//import com.example.demo.repositroy.*;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.text.SimpleDateFormat;
//import java.util.*;

//@SpringBootTest
class DemoApplicationTests {
//    @Autowired
//    UserMapper userMapper;
//    @Autowired
//    CommunityMapper communityMapper;
//    @Autowired
//    MessageMapper messageMapper;
//
//    @Test
//    void testInfo() {
//        System.out.println(inOutMapper.findInOutInfoByUserId(1L));
//    }
//
//    @Test
//    void contextLoads() {
////        User user = SpringContextUtil.getBean(User.class);
////        User user2 = SpringContextUtil.getBean(User.class);
////        user.setPassWord("12321");
////        System.out.println(user);
////        System.out.println(user2);
////        User user = new User();
////        user.setPassWord("test1");
////        user.setAccount("13302088103");
////        System.out.println(user);
////        System.out.println(userMapper.findUserByAccountAndPassword(user));
////        System.out.println(userMapper.findUserIdByPhone("13302088103"));
//    }
//
//    @Test
//    void testMessage() {
//        communityMapper.insertUserToCommunity(8, 1);
//        System.out.println(communityMapper.findAllUsersByCommunityId(1L));
////        System.out.println(communityMapper.findAll());
//    }
//
//    @Test
//    void testMessage_() {
////        Message msg = new Message();
////        msg.setId(1L);
////        msg.setContent("test");
////        msg.setTitle("test");
////        msg.setImportance(5);
////        messageMapper.insertMessage(1L, msg);
//        System.out.println(messageMapper.findMessagesByCommunityId(1L));
//    }
//
//    @Autowired
//    InOutMapper inOutMapper;
//
//    @Test
//    void testInOut() {
//        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
//        for (int i = 0; i < 1000000; i++) {
//            try {
//                Random random = new Random();
//                Long userId = (long) (random.nextInt(9) + 1);
//                Date[] dates = randomDates("2020-05-02", "2020-05-03");
//
//                Out out = new Out();
//                out.setFromCommunityId((long) (random.nextInt(3) + 1));
//                out.setComment("用户2跑出去了");
//                out.setOutTime(dates[0]);
//                out.setOutUserId(1L);
//                out.setUserId(userId);
//                String json = JSON.toJSONString(out);
//                inOutMapper.out(out);
//
//                In in = new In();
//                in.setComment("用户2跑了回来");
//                in.setInTime(dates[1]);
//                in.setInUserId(1L);
//                in.setUserId(userId);
//                in.setToCommunityId((long) (random.nextInt(3) + 1));
//                inOutMapper.in(in);
//                System.out.println(i);
//            } catch (Exception e) {
//                continue;
//            }
//        }
//    }
//
//    @Test
//    void testDate() {
//        for (int i = 0; i < 30; i++) {
//            Date[] dates = randomDates("2020-05-01", "2020-05-03");
//            System.out.print(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dates[0]));
//            System.out.println("--" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dates[1]));
//        }
//    }
//
//    private static Date[] randomDates(String beginDate, String endDate) {
//        Date date1 = randomDate(beginDate, endDate);
//        Date date2 = randomDate(beginDate, endDate);
//        if (date2.getTime() - date1.getTime() > 0) {
//            return new Date[]{date1, date2};
//        } else {
//            return new Date[]{date2, date1};
//        }
//    }
//
//
//    private static Date randomDate(String beginDate, String endDate) {
//        try {
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//            Date start = format.parse(beginDate);
//            Date end = format.parse(endDate);
//
//            if (start.getTime() >= end.getTime()) {
//                return null;
//            }
//            long date = random(start.getTime(), end.getTime());
//            return new Date(date);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private static long random(long begin, long end) {
//        long rtn = begin + (long) (Math.random() * (end - begin));
//        if (rtn == begin || rtn == end) {
//            return random(begin, end);
//        }
//        return rtn;
//    }
}
