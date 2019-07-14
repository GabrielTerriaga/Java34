package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {

	private Double pricePerHour;
	private Double pricePerDay;
	
	private TaxService taxService;
	
	public RentalService() {
		
	}

	public RentalService(Double pricePerHour, Double pricePerDay, TaxService taxService) {
		super();
		this.pricePerHour = pricePerHour;
		this.pricePerDay = pricePerDay;
		this.taxService = taxService;
	}
	
	public void processInvoice(CarRental carRental) {
		//calculo valor total aluguel carro
		
		long t1 = carRental.getStart().getTime();//getTime para pegar os milesegundos da data
		long t2 = carRental.getFinish().getTime();
		double hours = (double)(t2 - t1) / 1000 /60 /60;//double (casting) dentro para garantir esse valor quebrado para arredondar, t2 - t1 em milesegundos, dividido por 1000 para virar segundos, dividido por 60 para minutos e 60 para horas
		
		double basicPayment;
		if (hours <= 12.0) {
			basicPayment = Math.ceil(hours) * pricePerHour;//preco por hora
		}
		else {
			basicPayment = Math.ceil(hours / 24) * pricePerDay;//preco por dia
		}
		
		//taxas
		double tax = taxService.tax(basicPayment);
		
		//criado novo objeto e associado com o objeto de aluguel
		carRental.setInvoce(new Invoice(basicPayment, tax));
	}
	
	
}
