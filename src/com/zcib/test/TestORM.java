package com.zcib.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zcib.dao.OrderDao;
import com.zcib.domain.Address;
import com.zcib.domain.User;

public class TestORM {

	
	public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {

		String orderid = "2db113a946a14ba68d3c8673382cd1c2";
		OrderDao orderDao = new OrderDao();
//		System.out.println(orderDao.findById(orderid));
		System.out.println(orderDao.findAll(1));
		
		//		String className = "com.xiaoxiao.domain.User";
//		Class clazz = Class.forName(className);
//		//通过反射创建该对象
//		Object object = clazz.newInstance();
//		
//		Method[] methods =clazz.getMethods();//获取该类的所有方法
//		String methodName = "setUsername";
//		for(int i=0;i<methods.length;i++){
//			if(methods[i].getName().equals(methodName)){//获取和methodName相同的名字，调用它
//				methods[i].invoke(object,new Object[]{"王五"});//通过反射调用setUsername方法，并给它传参
//			}
//		}
//		methodName = "getUsername";
//		for(int i=0;i<methods.length;i++){
//			if(methods[i].getName().equals(methodName)){//获取和methodName相同的名字，调用它
//				System.out.println(methods[i].invoke(object));//通过反射调用getUsername方法
//			}
//		}
		
	}
	
	static void test1() throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class clazz = Class.forName("com.zcib.domain.User");
		Method[] methods =clazz.getMethods();//获取该类的所有方法
		for(int i=0;i<methods.length;i++){
			System.out.println(methods[i].getName());//打印该类的所有方法名
		}
		String methodName = "getUsername";
		User user = new User();
		user.setUsername("张三");
		for(int i=0;i<methods.length;i++){
			if(methods[i].getName().equals(methodName)){//获取和methodName相同的名字，调用它
				System.out.println(methods[i].invoke(user));//通过反射调用getUsername方法
			}
		}
		methodName = "setUsername";
		for(int i=0;i<methods.length;i++){
			if(methods[i].getName().equals(methodName)){//获取和methodName相同的名字，调用它
				Object object = "李四";
				methods[i].invoke(user,object);//通过反射调用setUsername方法，并给它传参
				
			}
		}
		System.out.println(user.getUsername());
	}
	
	public static Address toAddress(ResultSet rs) throws SQLException {
		Address address = new Address();
		if(rs.next()){
			address.setAddressid(rs.getInt("addressid"));
			address.setAddressname(rs.getString("addressname"));
			address.setPhone(rs.getString("phone"));
			address.setPostcode(rs.getString("postcode"));
			address.setReceiver(rs.getString("receiver"));
			address.setVipid(rs.getInt("vipid"));
		}
		return address;
	}


}
