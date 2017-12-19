package bulgogi1216.gmail.photogenic.Sungmin;

/**
 * Created by Sungmin on 2017-12-14.
 */

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

import bulgogi1216.gmail.photogenic.R;
import bulgogi1216.gmail.photogenic.database.CPJSONProcess;
import bulgogi1216.gmail.photogenic.database.CulturalProperty;
import bulgogi1216.gmail.photogenic.database.DBManager;
import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;

public class ChatBotActivity extends AppCompatActivity {
    private ChatView mChatView;
    private ArrayList<ChatMessage> mChatMessage;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sungmin_chatbot_activity);

        dbManager = new DBManager(getApplicationContext());
        //InitializeDatabase(dbManager);

        DBManager.SelectCondition selectCondition = new DBManager.SelectCondition();
        selectCondition.setProjection("*");
        selectCondition.setLimit("200");
        Cursor cursor = dbManager.select(selectCondition);

        if(cursor != null){
            if(cursor.moveToFirst()){
                do {
                    String id = cursor.getString(cursor.getColumnIndex("_id"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String category = cursor.getString(cursor.getColumnIndex("category"));
                    Double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
                    Double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
                    String address = cursor.getString(cursor.getColumnIndex(CulturalProperty.COL_ADDRESS));
                    Log.i("DB", id + " / " + title + " / " + category + " / " + latitude + " / " + longitude + " / " + address);
                }while(cursor.moveToNext());
            }
        }

        mChatView = (ChatView) findViewById(R.id.chat_view);
        mChatMessage = new ArrayList<ChatMessage>();

        mChatView.setOnSentMessageListener(new ChatView.OnSentMessageListener() {
            @Override
            public boolean sendMessage(ChatMessage chatMessage) {
                Log.i("send message : ", chatMessage.getMessage() + ", " + chatMessage.getFormattedTime() + ", " + chatMessage.getType()); // 됨
                //TextClear
                mChatView.getInputEditText().setText("");
                mChatMessage.add(chatMessage);
                MessageProcess mp = new MessageProcess();
                mp.execute("http://172.30.1.8:8080/chatbot/bot.php", chatMessage.getMessage());
                mChatView.addMessage(new ChatMessage(chatMessage.getMessage(), chatMessage.getTimestamp(), chatMessage.getType()));
                return false;
            }
        });

        mChatView.setTypingListener(new ChatView.TypingListener() {
            @Override
            public void userStartedTyping() {

            }

            @Override
            public void userStoppedTyping() {

            }
        });
    }

    public void InitializeDatabase(DBManager dbManager){
        String[] filenames = {"BuildingAndStructure", "HistoricalPlace","IndustrialAttraction","NaturalAttraction","NaturalResource","Observatory"};
        CPJSONProcess cpjsonProcess = new CPJSONProcess(getApplicationContext(), filenames);
        cpjsonProcess.startProcessing();
        JSONObject obj = cpjsonProcess.getRootObj();
        try {
            for (String filename : filenames) {
                JSONArray jsonArray = obj.getJSONObject(filename).getJSONArray("result");
                for (int i = 0; i < jsonArray.length(); i++) {
                    dbManager.insert(new CulturalProperty(jsonArray.getJSONObject(i)));

                }
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }

    //챗봇서버에서 받아온 답장 띄우기
    class MessageProcess extends AsyncTask<String, Void, String> {
        private String result = "";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("response", s);
            //Conditions : province | cultural property | creativity, Background, person, raters, rating, likes
            if("COND".compareTo(s.substring(0,3)) == 1)
            {
                Intent showPhotos = new Intent(ChatBotActivity.this, ShowRanking.class);
                StringTokenizer st = new StringTokenizer(s, ",");
                String skip = st.nextToken();
                showPhotos.putExtra("province",st.nextToken());
                showPhotos.putExtra("property",st.nextToken());
                showPhotos.putExtra("condition",st.nextToken());
                startActivity(showPhotos);
            }
            else
                mChatView.addMessage(new ChatMessage(s, System.currentTimeMillis(), ChatMessage.Type.RECEIVED));
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url=new URL(strings[0]);
                HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                conn.setConnectTimeout(10000);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                conn.connect();

                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes("message="+ Uri.encode(strings[1]));
                dos.flush();
                dos.close();

                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK)
                {
                    String line="";
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while((line = br.readLine()) !=null){
                        result +=line;
                    }
                    br.close();
                }

                conn.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }
    }
}

