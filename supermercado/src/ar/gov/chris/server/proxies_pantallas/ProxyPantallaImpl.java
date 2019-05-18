package ar.gov.chris.server.proxies_pantallas;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import ar.gov.chris.client.datos.DatosAutorizacion;
import ar.gov.chris.client.gwt.excepciones.GWT_ExcepcionBD;
import ar.gov.chris.client.interfaces.ProxyPantalla;
import ar.gov.mecon.componentes.server.annotations.NoAutenticarYAutorizar;
import ar.gov.mecon.genericos.bd.PoolDeConexiones;

public class ProxyPantallaImpl extends ProxyPantallaCHRISImpl implements ProxyPantalla {
	
	


		private static final long serialVersionUID = 4402255442885053601L;

		/** Nombre de la aplicación para las cookies.
		 */
		static final public String NOMBRE_APLICACION= "SUPERMERCADO";
		
		/** El login del usuario de CRM. Este usuario debe estar en SSO, tener el 
		 * servicio CRM, no estar bloqueado y no tener expirada la password. 
		 */
		static final public String LOGIN_USR_CRM= "crm_mecon";
		
		/** Inicialización de la clase.
		 * 
		 * @param config Servidor de configuración.
		 * @throws ServletException si hay algún problema con el servlet.
		 */
		@NoAutenticarYAutorizar
		public void init(ServletConfig config) throws ServletException {
			this.unica_conexion= false;
			try {
//				LectorConfiguracion.inicializar(config);
				int cant_conexiones= 2;//Integer.valueOf(LectorConfiguracion.
						//obtener_valor("cant_conexiones_iniciales_bd")).intValue();
				super.init(config, false, cant_conexiones, PoolDeConexiones.SIN_MAXIMO, true,
						 1, 10);
			} catch (ClassNotFoundException ex) {
				ex.printStackTrace();
				throw new ServletException(ex);
			}
		}

		/** Procesa la autorización de un usuario para una funcionalidad en particular.
		 * 
		 * @param funcionalidad Funcionalidad para la que se quiere autorizar.
		 * @return Datos de la autorización.
		 * @throws GWT_ExcepcionBD si hay algún problema con la BD.
		 */
		@NoAutenticarYAutorizar
		public DatosAutorizacion autorizado(String funcionalidad) throws GWT_ExcepcionBD {
			//La autenticación la hace una de las clases de más arriba.
			return super.autenticado_y_autorizado(funcionalidad);
		}
		
		/** Devuelve el string de la URL de la aplicación con el html incluido.
		 * @return Ídem.
		 */
		@NoAutenticarYAutorizar
		public String obtener_url_app(){
			HttpServletRequest request= this.getThreadLocalRequest();
			String context_path= request.getContextPath();
			String url= request.getScheme()+"://"+request.getServerName()+":"+
			 	request.getLocalPort()+
			 	(!context_path.isEmpty() ? context_path+"/" : "/") + "CasosSoporte.html";

			return url;
		}
	}
