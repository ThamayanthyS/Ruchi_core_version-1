package com.ruchi.hibernate.main;

import com.ruchi.hibernate.model.DAO.RestaurantDao;
import com.ruchi.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Thamayanthy on 12/22/2014.
 */
public class HibernateMain {
    public static void main(String[] args) {
        /*
        Session session = null;//= HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            RestaurantDao restaurant = new RestaurantDao();
//        restaurant.setCity_id("111111");
            restaurant.setRest_id("1408181771009");
//        restaurant.setRest_name("restaurant_name");
//

            CityDao cityDao = new CityDao();
            cityDao.setCity_id("1408181750584");

            FoodDao foodDao = new FoodDao();
            foodDao.setFood_id("1212212121");

            ReviewDao reviewDao = new ReviewDao();
            reviewDao.setRest_id("112121212");
            reviewDao.setReview("sfdasfasfasfaf");
            reviewDao.setReview_id("23232323");

            SentenceDao sentenceDao = new SentenceDao();
            sentenceDao.setReview_id("121231231231");
            sentenceDao.setFood_id("23423423423");
            sentenceDao.setSentence_location(2);

            SentimentDao sentiment = new SentimentDao();
            sentenceDao.setReview_id("1231231231");
            sentiment.setRest_id("23234234");


//            session = HibernateUtil.getSessionFactory().getCurrentSession();
            //start transaction
            session.beginTransaction();
            System.out.println(session.save(sentiment));
            //Save the Model object
            session.save(restaurant);
            //Commit transaction
            session.getTransaction().commit();
//        System.out.println("Restaurant ID="+restaurant.getRest_id());
//        System.out.println(session.get(Restaurant.class, (java.io.Serializable) restaurant.getRest_id()));
            //terminate session factory, otherwise program won't end
//            HibernateUtil.getSessionFactory().close();
        } catch (Exception e) {


        } finally {
            session.close();
            System.out.println("done");
        }
        **/

        Session sess = null;
        Transaction tran = null;
        Session session = null;//= HibernateUtil.getSessionFactory().getCurrentSession();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            tran = session.beginTransaction();
            List list = session.createQuery("from RestaurantDao").list();
            Iterator itr = list.iterator();
            while (itr.hasNext()) {
                RestaurantDao emp = (RestaurantDao) itr.next();
                System.out.print("restName: " + emp.getRest_name());

                System.out.println();
            }
            tran.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                session.close();
            }catch (Exception e){
                System.out.println("\n\nexception captured in finally");
            }
            System.out.println("done");
        }

    }
}
