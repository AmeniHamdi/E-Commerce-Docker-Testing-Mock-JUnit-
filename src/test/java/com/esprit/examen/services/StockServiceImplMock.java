package com.esprit.examen.services;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

import org.junit.jupiter.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.esprit.examen.entities.Stock;
import com.esprit.examen.repositories.StockRepository;

import java.util.Optional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
public class StockServiceImplMock {

	@Mock
	StockRepository sr;

	@InjectMocks
	IStockService ss = new StockServiceImpl();

	Stock stock1 = new Stock("Cake", 100, 1);
	Stock stock2 = new Stock("Chocolat", 200, 2);

	List<Stock> listStocks = new ArrayList<Stock>() {


		{
			add(new Stock("Fruits", 80, 10));
			add(new Stock("Légumes", 90, 20));
		}
	};

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void retrieveAllStocksTest() {
		when(sr.findAll()).thenReturn(listStocks);
		List<Stock> result = ss.retrieveAllStocks();
		Assertions.assertEquals(listStocks, result);
	}

	@Test
	public void addStockTest() {
		Stock stock3 = new Stock();
		stock3.setIdStock(3L);
		when(ss.addStock(stock3)).thenReturn(stock3);
		Stock result = ss.addStock(stock3);
		Assertions.assertEquals(stock3, result);
	}

	@Test
	public void deleteStockTest() {
		doNothing().when(sr).deleteById(1L);
		when(sr.findById(1L)).thenReturn(Optional.ofNullable(null));
		Stock result = ss.deleteStock(1L);
		Assertions.assertNull(result);
	}

	@Test
	public void updateStockTest() {
		when(sr.save(stock2)).thenReturn(stock2);
		Stock result = ss.updateStock(stock2);
		Assertions.assertEquals(result, stock2);
	}

	@Test
	public void retreiveStockTest() {
		when(sr.findById(1L)).thenReturn(Optional.of(stock1));
		Stock result = ss.retrieveStock(1L);
		Assertions.assertEquals(result, stock1);
	}

	@Test
	public void retrieveStatusStockTest() {
		when(sr.retrieveStatusStock()).thenReturn(listStocks);
		String result = ss.retrieveStatusStock();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String msgDate = sdf.format(now);
		Assertions.assertEquals(result, msgDate + "\n"
				+ ": le stock Fruits a une quantité de 80 inférieur à la quantité minimale a ne pas dépasser de 10\n"
				+ msgDate + "\n"
				+ ": le stock Légumes a une quantité de 90 inférieur à la quantité minimale a ne pas dépasser de 20\n");
	}

}
