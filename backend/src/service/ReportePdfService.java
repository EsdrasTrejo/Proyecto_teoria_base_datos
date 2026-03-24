/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import modelo.ReporteDistribucionGastosCategoria;
import org.openpdf.text.Document;
import org.openpdf.text.Image;
import org.openpdf.text.Paragraph;
import org.openpdf.text.Phrase;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import modelo.ReporteEstadoObligacionFija;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import modelo.ReporteTendenciaGastosCategoria;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import org.openpdf.text.Font;
import java.awt.Color;
import java.awt.Paint;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import modelo.ReporteCumplimientoPresupuesto;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;
import javax.imageio.ImageIO;
import org.openpdf.text.Document;
import org.openpdf.text.DocumentException;
import org.openpdf.text.Paragraph;
import org.openpdf.text.Phrase;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;
import modelo.ReporteResumenMensual;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import org.openpdf.text.Image;
import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;
/**
 *
 * @author Usuario
 */
public class ReportePdfService {

    public String exportarReporteResumenMensual(
        List<ReporteResumenMensual> datos,
        int idUsuario,
        int anioDesde,
        int mesDesde,
        int anioHasta,
        int mesHasta) {

    String rutaArchivo = "reporte_resumen_mensual_usuario_" + idUsuario + "_"
            + anioDesde + "_" + mesDesde + "_a_" + anioHasta + "_" + mesHasta + ".pdf";

    String rutaGrafico = generarGraficaResumenMensual(
            datos, idUsuario, anioDesde, mesDesde, anioHasta, mesHasta
    );

    Document document = new Document();

    try {
        PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
        document.open();

        Paragraph tituloSistema = new Paragraph("Sistema de Presupuesto Personal");
        tituloSistema.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(tituloSistema);

        Paragraph tituloReporte = new Paragraph("Reporte 1: Resumen Mensual de Ingresos vs Gastos vs Ahorros");
        tituloReporte.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(tituloReporte);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Usuario: " + idUsuario));
        document.add(new Paragraph("Periodo: " + mesDesde + "/" + anioDesde + " hasta " + mesHasta + "/" + anioHasta));
        document.add(new Paragraph(" "));

        Image grafico = Image.getInstance(rutaGrafico);
        grafico.scaleToFit(500, 300);
        grafico.setAlignment(Image.ALIGN_CENTER);
        document.add(grafico);

        document.add(new Paragraph(" "));

        PdfPTable tabla = new PdfPTable(6);
        tabla.setWidthPercentage(100);

        tabla.addCell("Año");
        tabla.addCell("Mes");
        tabla.addCell("Ingresos");
        tabla.addCell("Gastos");
        tabla.addCell("Ahorros");
        tabla.addCell("Balance");

        BigDecimal totalIngresos = BigDecimal.ZERO;
        BigDecimal totalGastos = BigDecimal.ZERO;
        BigDecimal totalAhorros = BigDecimal.ZERO;
        BigDecimal totalBalance = BigDecimal.ZERO;

        for (ReporteResumenMensual r : datos) {
            tabla.addCell(String.valueOf(r.getAnio()));
            tabla.addCell(String.valueOf(r.getMes()));
            tabla.addCell(r.getTotalIngresos().toString());
            tabla.addCell(r.getTotalGastos().toString());
            tabla.addCell(r.getTotalAhorros().toString());
            tabla.addCell(r.getBalanceFinal().toString());

            totalIngresos = totalIngresos.add(r.getTotalIngresos());
            totalGastos = totalGastos.add(r.getTotalGastos());
            totalAhorros = totalAhorros.add(r.getTotalAhorros());
            totalBalance = totalBalance.add(r.getBalanceFinal());
        }

        document.add(tabla);
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Resumen del periodo:"));
        document.add(new Paragraph("Ingresos acumulados: " + totalIngresos));
        document.add(new Paragraph("Gastos acumulados: " + totalGastos));
        document.add(new Paragraph("Ahorros acumulados: " + totalAhorros));
        document.add(new Paragraph("Balance acumulado: " + totalBalance));

    } catch (Exception e) {
        throw new RuntimeException("Error al generar PDF: " + e.getMessage(), e);
    } finally {
        document.close();
    }

    return rutaArchivo;
}
    
    private String generarGraficaResumenMensual(List<ReporteResumenMensual> datos,
                                            int idUsuario,
                                            int anioDesde,
                                            int mesDesde,
                                            int anioHasta,
                                            int mesHasta) {
    try {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (ReporteResumenMensual r : datos) {
            String etiquetaMes = String.format("%02d/%d", r.getMes(), r.getAnio());

            dataset.addValue(r.getTotalIngresos(), "Ingresos", etiquetaMes);
            dataset.addValue(r.getTotalGastos(), "Gastos", etiquetaMes);
            dataset.addValue(r.getTotalAhorros(), "Ahorros", etiquetaMes);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Resumen mensual de ingresos vs gastos vs ahorros",
                "Mes/Año",
                "Monto",
                dataset
        );

        CategoryPlot plot = chart.getCategoryPlot();

        String nombreArchivo = "grafico_reporte_resumen_" + idUsuario + "_"
                + anioDesde + "_" + mesDesde + "_a_" + anioHasta + "_" + mesHasta + ".png";

        BufferedImage imagen = chart.createBufferedImage(800, 400);
        ImageIO.write(imagen, "png", new File(nombreArchivo));

        return nombreArchivo;

    } catch (IOException e) {
        throw new RuntimeException("Error al generar la grafica: " + e.getMessage(), e);
    }
}
    
    private String generarGraficaDistribucionGastosCategoria(
        List<ReporteDistribucionGastosCategoria> datos,
        int idUsuario,
        int anio,
        int mes) {

    try {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

        for (ReporteDistribucionGastosCategoria r : datos) {
            dataset.setValue(r.getNombreCategoria(), r.getTotalGastado());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Distribucion de gastos por categoria",
                dataset,
                true,
                true,
                false
        );

        PiePlot<?> plot = (PiePlot<?>) chart.getPlot();
        plot.setCircular(true);

        String nombreArchivo = "grafico_reporte2_" + idUsuario + "_" + anio + "_" + mes + ".png";

        BufferedImage imagen = chart.createBufferedImage(700, 400);
        ImageIO.write(imagen, "png", new File(nombreArchivo));

        return nombreArchivo;

    } catch (Exception e) {
        throw new RuntimeException("Error al generar la grafica del reporte 2: " + e.getMessage(), e);
    }
}
    public String exportarReporteDistribucionGastosCategoria(
        List<ReporteDistribucionGastosCategoria> datos,
        int idUsuario,
        int anio,
        int mes) {

    String rutaArchivo = "reporte_distribucion_gastos_categoria_usuario_" 
            + idUsuario + "_" + anio + "_" + mes + ".pdf";

    String rutaGrafico = generarGraficaDistribucionGastosCategoria(datos, idUsuario, anio, mes);

    Document document = new Document();

    try {
        PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
        document.open();

        Paragraph tituloSistema = new Paragraph("Sistema de Presupuesto Personal");
        tituloSistema.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(tituloSistema);

        Paragraph tituloReporte = new Paragraph("Reporte 2: Distribucion de Gastos por Categoria");
        tituloReporte.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(tituloReporte);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Usuario: " + idUsuario));
        document.add(new Paragraph("Periodo: " + mes + "/" + anio));
        document.add(new Paragraph(" "));

        Image grafico = Image.getInstance(rutaGrafico);
        grafico.scaleToFit(450, 300);
        grafico.setAlignment(Image.ALIGN_CENTER);
        document.add(grafico);

        document.add(new Paragraph(" "));

        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);

        PdfPCell c1 = new PdfPCell(new Phrase("Categoria"));
        PdfPCell c2 = new PdfPCell(new Phrase("Total gastado"));
        PdfPCell c3 = new PdfPCell(new Phrase("Porcentaje"));
        PdfPCell c4 = new PdfPCell(new Phrase("Transacciones"));

        tabla.addCell(c1);
        tabla.addCell(c2);
        tabla.addCell(c3);
        tabla.addCell(c4);

        BigDecimal totalGeneral = BigDecimal.ZERO;
        int totalTransacciones = 0;

        for (ReporteDistribucionGastosCategoria r : datos) {
            tabla.addCell(r.getNombreCategoria());
            tabla.addCell(r.getTotalGastado().toString());
            tabla.addCell(r.getPorcentajeTotalGastos().toString() + "%");
            tabla.addCell(String.valueOf(r.getCantidadTransacciones()));

            totalGeneral = totalGeneral.add(r.getTotalGastado());
            totalTransacciones += r.getCantidadTransacciones();
        }

        document.add(tabla);
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Resumen del periodo:"));
        document.add(new Paragraph("Total general gastado: " + totalGeneral));
        document.add(new Paragraph("Cantidad total de transacciones: " + totalTransacciones));

    } catch (Exception e) {
        throw new RuntimeException("Error al generar PDF del reporte 2: " + e.getMessage(), e);
    } finally {
        document.close();
    }

    return rutaArchivo;
}
    
  public String exportarReporteCumplimientoPresupuesto(
        List<ReporteCumplimientoPresupuesto> datos,
        int idPresupuesto,
        int anio,
        int mes) {

    String rutaArchivo = "reporte_cumplimiento_presupuesto_"
            + idPresupuesto + "_" + anio + "_" + mes + ".pdf";

    String rutaGrafico = generarGraficaCumplimientoPresupuesto(datos, idPresupuesto, anio, mes);

    Document document = new Document();

    try {
        PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
        document.open();

        Font tituloFont = new Font(Font.HELVETICA, 14, Font.BOLD);
        Font subtituloFont = new Font(Font.HELVETICA, 11, Font.NORMAL);

        Paragraph tituloSistema = new Paragraph("Sistema de Presupuesto Personal", tituloFont);
        tituloSistema.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(tituloSistema);

        Paragraph tituloReporte = new Paragraph(
                "Reporte 3: Cumplimiento de Presupuesto por Categoria y Subcategoria",
                tituloFont
        );
        tituloReporte.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(tituloReporte);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Presupuesto: " + idPresupuesto, subtituloFont));
        document.add(new Paragraph("Periodo: " + mes + "/" + anio, subtituloFont));
        document.add(new Paragraph(" "));

        Image grafico = Image.getInstance(rutaGrafico);
        grafico.scaleToFit(520, 300);
        grafico.setAlignment(Image.ALIGN_CENTER);
        document.add(grafico);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Indicadores: Verde < 80% | Amarillo 80%-100% | Rojo > 100%"));
        document.add(new Paragraph(" "));

        PdfPTable tabla = new PdfPTable(7);
        tabla.setWidthPercentage(100);

        tabla.addCell(new PdfPCell(new Phrase("Categoria")));
        tabla.addCell(new PdfPCell(new Phrase("Subcategoria")));
        tabla.addCell(new PdfPCell(new Phrase("Presupuestado")));
        tabla.addCell(new PdfPCell(new Phrase("Ejecutado")));
        tabla.addCell(new PdfPCell(new Phrase("Diferencia")));
        tabla.addCell(new PdfPCell(new Phrase("% Ejecucion")));
        tabla.addCell(new PdfPCell(new Phrase("Indicador")));

        BigDecimal totalPresupuestado = BigDecimal.ZERO;
        BigDecimal totalEjecutado = BigDecimal.ZERO;
        BigDecimal totalDiferencia = BigDecimal.ZERO;

        for (ReporteCumplimientoPresupuesto r : datos) {
            tabla.addCell(r.getNombreCategoria());
            tabla.addCell(r.getNombreSubcategoria());
            tabla.addCell(r.getMontoPresupuestado().toString());
            tabla.addCell(r.getMontoEjecutado().toString());
            tabla.addCell(r.getDiferencia().toString());
            tabla.addCell(r.getPorcentajeEjecucion().toString() + "%");

            PdfPCell celdaIndicador = new PdfPCell(new Phrase(r.getIndicadorVisual()));

            String indicador = r.getIndicadorVisual() == null ? "" : r.getIndicadorVisual().toLowerCase();

            if (indicador.equals("verde")) {
                celdaIndicador.setBackgroundColor(Color.GREEN);
            } else if (indicador.equals("amarillo")) {
                celdaIndicador.setBackgroundColor(Color.YELLOW);
            } else if (indicador.equals("rojo")) {
                celdaIndicador.setBackgroundColor(Color.RED);
            }

            tabla.addCell(celdaIndicador);

            totalPresupuestado = totalPresupuestado.add(r.getMontoPresupuestado());
            totalEjecutado = totalEjecutado.add(r.getMontoEjecutado());
            totalDiferencia = totalDiferencia.add(r.getDiferencia());
        }

        document.add(tabla);
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Resumen general:"));
        document.add(new Paragraph("Total presupuestado: " + totalPresupuestado));
        document.add(new Paragraph("Total ejecutado: " + totalEjecutado));
        document.add(new Paragraph("Total diferencia: " + totalDiferencia));

    } catch (Exception e) {
        throw new RuntimeException("Error al generar PDF del reporte 3: " + e.getMessage(), e);
    } finally {
        document.close();
    }

    return rutaArchivo;
}
  private String generarGraficaCumplimientoPresupuesto(
        List<ReporteCumplimientoPresupuesto> datos,
        int idPresupuesto,
        int anio,
        int mes) {

    try {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (ReporteCumplimientoPresupuesto r : datos) {
            String categoria = r.getNombreSubcategoria();

            dataset.addValue(r.getMontoPresupuestado(), "Presupuestado", categoria);
            dataset.addValue(r.getMontoEjecutado(), "Ejecutado", categoria);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Cumplimiento de presupuesto por subcategoria",
                "Subcategoria",
                "Monto",
                dataset
        );

        CategoryPlot plot = chart.getCategoryPlot();

        BarRenderer renderer = new BarRenderer() {
            @Override
            public Paint getItemPaint(int row, int column) {
                if (row == 0) {
                    return Color.GRAY;
                }

                ReporteCumplimientoPresupuesto r = datos.get(column);
                String indicador = r.getIndicadorVisual() == null ? "" : r.getIndicadorVisual().toLowerCase();

                switch (indicador) {
                    case "verde":
                        return Color.GREEN;
                    case "amarillo":
                        return Color.YELLOW;
                    case "rojo":
                        return Color.RED;
                    default:
                        return Color.BLUE;
                }
            }
        };

        plot.setRenderer(renderer);

        CategoryAxis axis = plot.getDomainAxis();
        axis.setMaximumCategoryLabelLines(3);

        String nombreArchivo = "grafico_reporte3_" + idPresupuesto + "_" + anio + "_" + mes + ".png";

        BufferedImage imagen = chart.createBufferedImage(1000, 500);
        ImageIO.write(imagen, "png", new File(nombreArchivo));

        return nombreArchivo;

    } catch (Exception e) {
        throw new RuntimeException("Error al generar la grafica del reporte 3: " + e.getMessage(), e);
    }
}
  private String generarGraficaTendenciaGastosCategoria(
        List<ReporteTendenciaGastosCategoria> datos,
        int idUsuario,
        int anioDesde,
        int mesDesde,
        int anioHasta,
        int mesHasta) {

    try {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (ReporteTendenciaGastosCategoria r : datos) {
            String periodo = String.format("%02d/%d", r.getMes(), r.getAnio());
            dataset.addValue(r.getTotalGastado(), r.getNombreCategoria(), periodo);
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Tendencia de gastos por categoria",
                "Mes/Año",
                "Monto gastado",
                dataset
        );

        String nombreArchivo = "grafico_reporte4_" + idUsuario + "_"
                + anioDesde + "_" + mesDesde + "_a_" + anioHasta + "_" + mesHasta + ".png";

        BufferedImage imagen = chart.createBufferedImage(900, 450);
        ImageIO.write(imagen, "png", new File(nombreArchivo));

        return nombreArchivo;

    } catch (Exception e) {
        throw new RuntimeException("Error al generar la grafica del reporte 4: " + e.getMessage(), e);
    }
}
  public String exportarReporteTendenciaGastosCategoria(
        List<ReporteTendenciaGastosCategoria> datos,
        int idUsuario,
        int anioDesde,
        int mesDesde,
        int anioHasta,
        int mesHasta) {

    String rutaArchivo = "reporte_tendencia_gastos_categoria_"
            + idUsuario + "_" + anioDesde + "_" + mesDesde + "_a_" + anioHasta + "_" + mesHasta + ".pdf";

    String rutaGrafico = generarGraficaTendenciaGastosCategoria(
            datos, idUsuario, anioDesde, mesDesde, anioHasta, mesHasta
    );

    Document document = new Document();

    try {
        PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
        document.open();

        Paragraph tituloSistema = new Paragraph("Sistema de Presupuesto Personal");
        tituloSistema.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(tituloSistema);

        Paragraph tituloReporte = new Paragraph("Reporte 4: Tendencia de Gastos por Categoria en el Tiempo");
        tituloReporte.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(tituloReporte);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Usuario: " + idUsuario));
        document.add(new Paragraph("Periodo: " + mesDesde + "/" + anioDesde + " hasta " + mesHasta + "/" + anioHasta));
        document.add(new Paragraph(" "));

        Image grafico = Image.getInstance(rutaGrafico);
        grafico.scaleToFit(520, 300);
        grafico.setAlignment(Image.ALIGN_CENTER);
        document.add(grafico);

        document.add(new Paragraph(" "));

        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);

        tabla.addCell("Categoria");
        tabla.addCell("Año");
        tabla.addCell("Mes");
        tabla.addCell("Total gastado");

        BigDecimal totalGeneral = BigDecimal.ZERO;

        for (ReporteTendenciaGastosCategoria r : datos) {
            tabla.addCell(r.getNombreCategoria());
            tabla.addCell(String.valueOf(r.getAnio()));
            tabla.addCell(String.valueOf(r.getMes()));
            tabla.addCell(r.getTotalGastado().toString());

            totalGeneral = totalGeneral.add(r.getTotalGastado());
        }

        document.add(tabla);
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Total general gastado en el periodo: " + totalGeneral));

    } catch (Exception e) {
        throw new RuntimeException("Error al generar PDF del reporte 4: " + e.getMessage(), e);
    } finally {
        document.close();
    }

    return rutaArchivo;
}
  
  private String generarGraficaEstadoObligacionesFijas(
        List<ReporteEstadoObligacionFija> datos,
        int idUsuario,
        int anio,
        int mes) {

    try {
        int pagadas = 0;
        int pendientes = 0;
        int porVencer = 0;
        int vencidas = 0;

        for (ReporteEstadoObligacionFija r : datos) {
            String estado = r.getEstadoPago() == null ? "" : r.getEstadoPago().toLowerCase();

            switch (estado) {
                case "pagada":
                    pagadas++;
                    break;
                case "pendiente":
                    pendientes++;
                    break;
                case "por vencer":
                    porVencer++;
                    break;
                case "vencida":
                    vencidas++;
                    break;
            }
        }

        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        dataset.setValue("Pagadas", pagadas);
        dataset.setValue("Pendientes", pendientes);
        dataset.setValue("Por vencer", porVencer);
        dataset.setValue("Vencidas", vencidas);

        JFreeChart chart = ChartFactory.createPieChart(
                "Resumen de obligaciones por estado",
                dataset,
                true,
                true,
                false
        );

        String nombreArchivo = "grafico_reporte5_" + idUsuario + "_" + anio + "_" + mes + ".png";

        BufferedImage imagen = chart.createBufferedImage(700, 400);
        ImageIO.write(imagen, "png", new File(nombreArchivo));

        return nombreArchivo;

    } catch (Exception e) {
        throw new RuntimeException("Error al generar la grafica del reporte 5: " + e.getMessage(), e);
    }
}
  
  
  public String exportarReporteEstadoObligacionesFijas(
        List<ReporteEstadoObligacionFija> datos,
        int idUsuario,
        int anio,
        int mes) {

    String rutaArchivo = "reporte_estado_obligaciones_fijas_"
            + idUsuario + "_" + anio + "_" + mes + ".pdf";

    String rutaGrafico = generarGraficaEstadoObligacionesFijas(datos, idUsuario, anio, mes);

    Document document = new Document();

    try {
        PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
        document.open();

        Paragraph tituloSistema = new Paragraph("Sistema de Presupuesto Personal");
        tituloSistema.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(tituloSistema);

        Paragraph tituloReporte = new Paragraph("Reporte 5: Estado de Obligaciones Fijas y Cumplimiento de Pagos");
        tituloReporte.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(tituloReporte);

        document.add(new Paragraph(" "));
        document.add(new Paragraph("Usuario: " + idUsuario));
        document.add(new Paragraph("Periodo: " + mes + "/" + anio));
        document.add(new Paragraph(" "));

        Image grafico = Image.getInstance(rutaGrafico);
        grafico.scaleToFit(450, 280);
        grafico.setAlignment(Image.ALIGN_CENTER);
        document.add(grafico);

        document.add(new Paragraph(" "));

        PdfPTable tabla = new PdfPTable(7);
        tabla.setWidthPercentage(100);

        tabla.addCell("Obligacion");
        tabla.addCell("Categoria");
        tabla.addCell("Monto mensual");
        tabla.addCell("Fecha vencimiento");
        tabla.addCell("Estado");
        tabla.addCell("Dias ref.");
        tabla.addCell("Ultimo pago");

        for (ReporteEstadoObligacionFija r : datos) {
            tabla.addCell(r.getNombreObligacion());
            tabla.addCell(r.getNombreCategoria());
            tabla.addCell(r.getMontoMensual().toString());
            tabla.addCell(String.valueOf(r.getFechaVencimiento()));

            PdfPCell celdaEstado = new PdfPCell(new Phrase(r.getEstadoPago()));
            String estado = r.getEstadoPago() == null ? "" : r.getEstadoPago().toLowerCase();

            if (estado.equals("pagada")) {
                celdaEstado.setBackgroundColor(Color.GREEN);
            } else if (estado.equals("pendiente")) {
                celdaEstado.setBackgroundColor(Color.YELLOW);
            } else if (estado.equals("por vencer")) {
                celdaEstado.setBackgroundColor(Color.ORANGE);
            } else if (estado.equals("vencida")) {
                celdaEstado.setBackgroundColor(Color.RED);
            }

            tabla.addCell(celdaEstado);
            tabla.addCell(String.valueOf(r.getDiasReferencia()));
            tabla.addCell(String.valueOf(r.getFechaUltimoPago()));
        }

        document.add(tabla);

    } catch (Exception e) {
        throw new RuntimeException("Error al generar PDF del reporte 5: " + e.getMessage(), e);
    } finally {
        document.close();
    }

    return rutaArchivo;
}
}