package project.spring.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import project.spring.domain.Cake;

@Component
@Transactional
public class CakeManagerImp implements CakeManager {
	
	@Autowired
	private SessionFactory session;
	
	public SessionFactory getSessionFactory() {
		return session;
	}
	
	public void setSessionFactory(SessionFactory session) {
		this.session = session;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Cake> getAllCakes() {
		return session.getCurrentSession().getNamedQuery("Cake.getAll").list();
	}
	
	@Override
	public Cake getCakeById(Cake cake) {
		return (Cake) session.getCurrentSession().get(Cake.class, cake.getId());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Cake> getCakeByName(String name) {
		return session.getCurrentSession().getNamedQuery("Cake.getByName").setString("name", name).list();
	}
	
	@Override
	public void addCake(Cake cake) {
		cake.setId(null);
		session.getCurrentSession().persist(cake);
	}
	
	@Override
	public void editCake(Cake cake) {
		session.getCurrentSession().update(cake);
	}
	
	@Override
	public void removeCake(Cake cake) {
		session.getCurrentSession().delete(cake);
	}
}
