package org.example.utils;

import com.google.gson.*;
import javafx.scene.control.Alert;
import org.example.models.TransactionCategory;
import org.example.models.User;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SqlUtil {

    //get
    public static User getUserByEmail(String userEmail){
        //authenticate email and password
        HttpURLConnection conn=null;
        try{
            conn= ApiUtil.fetchApi(
                    "/api/v1/user?email="+userEmail,
                    ApiUtil.RequestMethod.GET,null
            );


            if(conn.getResponseCode()!=200){
                System.out.println("Error(getUserByEmail): "+conn.getResponseCode());
                return null;
            }

            String userDataJson=ApiUtil.readApiResponse(conn);

            JsonObject jsonObject= JsonParser.parseString(userDataJson).getAsJsonObject();

            //extract the json data
            int id=jsonObject.get("id").getAsInt();
            String name=jsonObject.get("name").getAsString();
            String email=jsonObject.get("email").getAsString();
            String password=jsonObject.get("password").getAsString();
            LocalDateTime createAt=new Gson().fromJson(jsonObject.get("createAt"), LocalDateTime.class);

            return  new User(id,name,email,password,createAt);

        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if(conn!=null){
                conn.disconnect();
            }
        }
        return null;

    }


    public static List<TransactionCategory> getAllTransactionCategoriesByUser(User user){
        List<TransactionCategory> categories=new ArrayList<>();
        HttpURLConnection conn=null;
        try{
            conn=ApiUtil.fetchApi(
                    "/api/v1/transaction-category/user/"+user.getId(),
                    ApiUtil.RequestMethod.GET,null
            );

            if(conn.getResponseCode()!=200){
                System.out.println("Error(getAllTransactionCategoriesByUser): "+conn.getResponseCode());
            }

            String result=ApiUtil.readApiResponse(conn);
            JsonArray resultJsonArray=new JsonParser().parse(result).getAsJsonArray();

            for(JsonElement jsonElement:resultJsonArray){
                int categoryId=jsonElement.getAsJsonObject().get("id").getAsInt();
                String categoryName=jsonElement.getAsJsonObject().get("categoryName").getAsString();
                String categoryColor=jsonElement.getAsJsonObject().get("categoryColor").getAsString();

                categories.add(new TransactionCategory(categoryId,categoryName,categoryColor));
            }
            return   categories;
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(conn!=null)
                conn.disconnect();
        }

    return  null;
    }



    //post
    public static boolean postLoginUser(String email, String password){
        //authenticate email and password
        HttpURLConnection conn=null;
        try{
            conn= ApiUtil.fetchApi(
                    "/api/v1/user/login?email="+email+"&password="+password,
                    ApiUtil.RequestMethod.POST,null
            );

            if(conn.getResponseCode()!=200){
              return  false;
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if(conn!=null){
                conn.disconnect();
            }
        }
        return true;
    }

    public static boolean postCreateUser(JsonObject userData){
        HttpURLConnection conn=null;
        try{
            conn=ApiUtil.fetchApi(
                    "/api/v1/user",
                    ApiUtil.RequestMethod.POST,
                    userData
            );

            if(conn.getResponseCode()!=200){
                return false;//failed to create
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(conn!=null)
                conn.disconnect();
        }

        return true;//successfully
    }

    public  static boolean postTransactionCategory(JsonObject transactionCategoryData){
        HttpURLConnection conn = null;
        try{
            conn= ApiUtil.fetchApi(
                    "/api/v1/transaction-category",
                    ApiUtil.RequestMethod.POST,
                    transactionCategoryData
            );

            if(conn.getResponseCode()!=200){
                return false;
            }

            return   true;
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if(conn!=null){
                conn.disconnect();
            }
        }

        return false;
    }


    //update
    public static boolean putTransactionCategory(int categoryId, String newCategoryName,String newCategoryColor){
        HttpURLConnection conn = null;
        try{
            conn= ApiUtil.fetchApi(
                    "/api/v1/transaction-category/"+categoryId+"?newCategoryName="+ newCategoryName +
                    "&newCategoryColor=" + newCategoryColor,
                    ApiUtil.RequestMethod.PUT,
                    null
            );

            if(conn.getResponseCode()!=200){
                System.out.println("Error(putTransactionCategory): "+conn.getResponseCode());
                return false;
            }

            return   true;
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if(conn!=null){
                conn.disconnect();
            }
        }

        return false;
    }

    //delete
    public static boolean deleteTransactionCategoryByID(int categoryId){
        HttpURLConnection conn = null;
        try{
            conn= ApiUtil.fetchApi(
                    "/api/v1/transaction-category/"+categoryId,
                    ApiUtil.RequestMethod.DELETE,
                    null
            );

            if(conn.getResponseCode()!=200){
                System.out.println("Error(deleteTransactionCategoryById): "+conn.getResponseCode());
                return false;
            }

            return   true;
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if(conn!=null){
                conn.disconnect();
            }
        }

        return false;

    }


}

