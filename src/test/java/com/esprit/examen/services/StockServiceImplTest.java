package com.esprit.examen.services;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.esprit.examen.entities.Stock;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@Slf4j
public class StockServiceImplTest {
	@Autowired
	IStockService stockService;

	/*
	 * @Test public void testAddStock() { List<Stock> stocks =
	 * stockService.retrieveAllStocks(); int expected = stocks.size(); List<Stock>
	 * finalList =new ArrayList<>(); finalList.addAll(stocks); int
	 * finalListSize=finalList.size() ; Stock s = new Stock();
	 * s.setLibelleStock("stock test"); s.setQte(30); s.setQteMin(60); Stock
	 * savedStock= stockService.addStock(s); assertEquals(expected+1,finalListSize +
	 * 1 ); assertNotNull(savedStock.getLibelleStock());
	 * stockService.deleteStock(savedStock.getIdStock()); }
	 */

	@Test
	@Order(3)
	public void testRetrieveAllStocks() {
		List<Stock> stocks = stockService.retrieveAllStocks();
		Assertions.assertEquals(5, stocks.size());

	}

	@Test
	@Order(1)
	public void testAddStock() {

		List<Stock> stocks = stockService.retrieveAllStocks();
		int expected = stocks.size();
		Stock s = new Stock("stock test", 10, 100);
		Stock savedStock = stockService.addStock(s);

		assertEquals(expected + 1, stockService.retrieveAllStocks().size());
		assertNotNull(savedStock.getLibelleStock());
		stockService.deleteStock(savedStock.getIdStock());

	}

	@Test
	@Order(2)
	public void testAddStockOptimized() {

		Stock s = new Stock("stock test", 10, 100);
		Stock savedStock = stockService.addStock(s);
		log.info("Stock ADDED" + stockService.addStock(s));
		assertNotNull(savedStock.getIdStock());
		assertSame(10, savedStock.getQte());
		assertTrue(savedStock.getQteMin() > 0);
		stockService.deleteStock(savedStock.getIdStock());

	}

	@Test
	@Order(4)
	public void testDeleteStock() {
		Stock s = new Stock("stock test", 10, 100);
		Stock savedStock = stockService.addStock(s);
		stockService.deleteStock(savedStock.getIdStock());
		log.info("stock deleted");
		assertNull(stockService.retrieveStock(savedStock.getIdStock()));
	}

}
