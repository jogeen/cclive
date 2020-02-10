package com.jogeen.cclive;

import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class NameCheck {

    public static void main(String[] args) throws IOException {
        String useid="44CACEB725BAB224";
        String roomid="F4F059CEBD50BB899C33DC5901307461";
        String pathname = NameCheck.class.getClassLoader().getResource("name.txt").getPath();
        //读文件获取姓名
        List<String> names = readFile(pathname);
        if(names.size()==0){
            throw new RuntimeException("读取文件失败,或文件内容为空！");
        }else{
            System.out.printf("读取%s个姓名\r\n",names.size());
        }
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("获取在线人员名单....");
        ResponseEntity<String> response = restTemplate.getForEntity("https://view.csslcloud.net/api/inline/room/onlineusers?&userid="+useid+"&roomid="+roomid+"&value=&type=2", String.class);
        Map map = new Gson().fromJson(response.getBody(), Map.class);
        List<Map<String,String>> onlineUsers = (List<Map<String,String>>)map.get("onlineUsers");
        Set<String> onlineSet=new HashSet<String>();
        for (int i = 0; i < onlineUsers.size(); i++) {
            Map<String, String> user = onlineUsers.get(i);
            String[] name = user.get("name").split("-");
            onlineSet.add(name.length>1?name[1]:name[0]);
        }
        System.out.printf("在线人数%s\r\n",onlineSet.size());
        //检查人员
        System.out.println("-------以下是未在直播间人员----------");
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            if(!onlineSet.contains(name)){
                System.out.println(name);
            }
        }

    }

    public static List<String> readFile(String pathname) {
        List<String> list=new ArrayList<String>();
        try{
            FileReader reader = new FileReader(pathname);
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
