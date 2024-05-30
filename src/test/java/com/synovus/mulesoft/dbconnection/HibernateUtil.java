package com.synovus.mulesoft.dbconnection;

import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Lazy;
import com.synovus.mulesoft.dbconnection.models.EmployeeTb;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();

	@Lazy
	public static synchronized SessionFactory buildSessionFactory() {
		if (sessionFactory == null) {
			try {
				return new Configuration().configure().buildSessionFactory();
			} catch (Throwable ex) {
				System.err.println("Initial SessionFactory creation failed." + ex);
				throw new ExceptionInInitializerError(ex);
			}
		}
		return sessionFactory;
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static <R> List RunDataBaseQuery(String hql, Class<R> modelclassname) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		try (Session session = sessionFactory.openSession()) {
			Transaction transaction = session.beginTransaction();
			try {
				Query<EmployeeTb> query = (Query<EmployeeTb>) session.createQuery(hql, modelclassname);
				List<EmployeeTb> employees = query.getResultList();
				return employees;
			} catch (Exception e) {
				if (transaction != null) {
					transaction.rollback();
				}
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}