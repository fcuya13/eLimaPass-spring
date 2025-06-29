package com.francocuya13.elimapassspring.integration.steps;

import com.francocuya13.elimapassspring.controllers.RecargaController;
import com.francocuya13.elimapassspring.models.Recarga;
import com.francocuya13.elimapassspring.repositories.RecargaRepository;
import com.francocuya13.elimapassspring.services.RecargaService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RecargasSteps {

    @Autowired
    private RecargaController recargaController;

    @Autowired
    private RecargaService recargaService;

    @Autowired
    private RecargaRepository recargaRepository;

    private RecargaController.RecargaRequest recargaRequest;
    String codigotarjeta;
    List<Recarga> recargas;

    @Given("recarga con {string} de {int} soles de la tarjeta {string}")
    public void recargaCon(String medioPago, double monto, String codigoTarjeta) {
        recargaRequest = new RecargaController.RecargaRequest();
        recargaRequest.setCodigoTarjeta(codigoTarjeta);
        recargaRequest.setMedioPago(medioPago);
        recargaRequest.setMontoRecargado(monto);
    }

    @When("recargo la tarjeta")
    public void recargar() {
        try {
            recargaController.recargarTarjeta(recargaRequest);
        } catch (Exception e) {
        throw new RuntimeException(e);
        }
    }

    @Then("se genera recarga con {string}")
    public void seGeneraRecargaCon(String medioPago) {
        assertNotNull(recargaRepository.findFirstByMedioPago(medioPago));
    }


    @Given("tengo una tarjeta con codigo {string}")
    public void queTengoUnaTarjetaConCodigo(String codigo) {
        codigotarjeta = codigo;
    }


    @When("quiero ver mi historial de recargas")
    public void quieroVerMiHistorialDeRecargas() {
        recargas = recargaService.getHistorialRecarga(codigotarjeta);
    }

    @Then("debo ver las recargas más recientes ordenadas por fecha descendente")
    public void deboVerLasNRecargasMasRecientesOrdenadas() {
        assertNotNull(recargas);
        assert(!recargas.isEmpty());
    }

}
