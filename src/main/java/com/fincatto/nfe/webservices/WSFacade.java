package com.fincatto.nfe.webservices;

import com.fincatto.nfe.NFEConfig;
import com.fincatto.nfe.classes.NFUnidadeFederativa;
import com.fincatto.nfe.classes.lote.consulta.NFLoteConsultaRetorno;
import com.fincatto.nfe.classes.lote.envio.NFLoteEnvio;
import com.fincatto.nfe.classes.lote.envio.NFLoteEnvioRetorno;
import com.fincatto.nfe.classes.statusservico.consulta.NFStatusServicoConsultaRetorno;
import com.fincatto.nfe.validadores.xsd.XMLValidador;

public class WSFacade {
    private final WSLoteEnvio wsLoteEnvio;
    private final WSLoteConsulta wsLoteConsulta;
    private final WSStatusConsulta wsStatusConsulta;

    private final String CAMINHO_SCHEMA_XSD;

    public WSFacade(final NFEConfig config) {
        this.CAMINHO_SCHEMA_XSD = this.getClass().getResource("../../../../schemas/v2/enviNFe_v2.00.xsd").getFile();
        System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");
        System.setProperty("javax.net.ssl.trustStore", config.getCadeiaCertificados().getAbsolutePath());
        System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
        System.setProperty("javax.net.ssl.keyStore", config.getCertificado().getAbsolutePath());
        System.setProperty("javax.net.ssl.keyStorePassword", config.getCertificadoSenha());

        this.wsLoteEnvio = new WSLoteEnvio(config);
        this.wsLoteConsulta = new WSLoteConsulta(config);
        this.wsStatusConsulta = new WSStatusConsulta(config);
    }

    public NFLoteEnvioRetorno enviaLote(final NFLoteEnvio lote, final NFUnidadeFederativa uf) throws Throwable {
        XMLValidador.valida(lote.toString(), this.CAMINHO_SCHEMA_XSD);
        return this.wsLoteEnvio.enviaLote(lote, uf);
    }

    public NFLoteConsultaRetorno consultaLote(final String numeroRecibo, final NFUnidadeFederativa uf) throws Throwable {
        return this.wsLoteConsulta.consultaLote(numeroRecibo, uf);
    }

    public NFStatusServicoConsultaRetorno consultaStatus(final NFUnidadeFederativa uf) throws Throwable {
        return this.wsStatusConsulta.consultaStatus(uf);
    }
}