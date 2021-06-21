create database DBTonysKinal2016416;
use DBTonysKinal2016416;



               #  = ---- Productos --- =
 Delimiter $$
 create procedure sp_Crear_Productos()
  begin
 create table Productos( 
 codigoProducto int not null primary key auto_increment,
 nombreProducto varchar(150),
 cantidad int
 );
 end $$
 Delimiter ;
              
           

            #  = --- Tipo Plato --- =
Delimiter $$
create procedure sp_Crear_TipoPlato()
begin
create table TipoPlato(
codigoTipoPlato int not null primary key auto_increment,
descripcion varchar(100)
);
end$$
Delimiter ;


            
		#  = --- Tipo Empleado--- =
Delimiter $$
create procedure sp_Crear_TipoEmpleado()
begin
create table TipoEmpleado(
codigoTipoEmpleado int not null primary key auto_increment,
descripcion varchar(100)
);
end $$
Delimiter ;

            



       #  = --- Empresas --- =
Delimiter $$
create procedure sp_Crear_Empresas()
begin 
create table Empresas(
codigoEmpresa int not null primary key auto_increment,
nombreEmpresa varchar(150),
direccion varchar(150),
telefono varchar(10)
);
end $$
Delimiter ;

              


     # = --- Presupuesto --- =
Delimiter $$
create procedure sp_Crear_Presupuesto()
begin
create table Presupuesto(
codigoPresupuesto int not null primary key auto_increment,
foreign key (Empresas_codigoEmpresa) references Empresas(codigoEmpresa),
fechaSolicitud date,
cantidadPresupuesto decimal (10,2),
Empresas_codigoEmpresa int

);
end $$
Delimiter ;

           


        # = -- Servicios---=
Delimiter $$
create procedure sp_Crear_Servicios()
begin
create table Servicios(
codigoServicio int not null primary key auto_increment,
foreign key (Empresas_CodigoEmpresa) references Empresas (CodigoEmpresa) ,
fechaServicio date,
tipoServicio varchar(100),
horaServicio time,
lugarServicio varchar(100),
telefonoContacto varchar(10),
Empresas_CodigoEmpresa int

);
end $$
Delimiter ;

 

       # = --- Platos --- =
Delimiter $$
create procedure sp_Crear_Platos()
begin
create table Platos(
codigoPlato int not null primary key auto_increment,
foreign key (TipoPlato_codigoTipoPlato) references TipoPlato(codigoTipoPlato) ,
cantidad int,
nombrePlato varchar(50),
descripcionPlato varchar(150),
precioPlato decimal(10,2),
TipoPlato_codigoTipoPlato int

);
end $$
Delimiter ;


         


        #--  Producto_has_Platos
Delimiter $$
create procedure sp_Crear_Productos_has_Platos()
begin
create table Productos_has_Platos(
codigo_has_Platos int not null primary key auto_increment,
foreign key (Productos_CodigoProductos) references Productos (codigoProducto) ,
foreign key (Platos_CodigoPlato) references Platos(codigoPlato) ,
Productos_CodigoProductos int,
Platos_CodigoPlato int

);
end $$
Delimiter ;

         


       # -- Servicios_has_Platos
Delimiter $$
create procedure sp_Crear_Servicios_has_Platos()
begin
create table Servicios_has_Platos(
codigoServicios_has_Platos int not null primary key auto_increment,
foreign key (Servicios_CodigoServicio) references Servicios(codigoServicio) ,
foreign key (Platos_codigoPlato) references Platos(codigoPlato) ,
Servicios_CodigoServicio int,
Platos_CodigoPlato int

);
end $$
Delimiter ;


        

      # = ---Empleados--- = 
Delimiter $$
create procedure sp_Crear_Empleados()
begin
create table Empleados(
foreign key (TipoEmpleado_codigoTipoEmpleado) references TipoEmpleado(codigoTipoEmpleado) ,
codigoEmpleado int not null primary key auto_increment,
numeroEmpleado int,
apellidosEmpleado varchar(150),
nombresEmpleado varchar(150),
direccionEmpleado varchar(150),
telefonoContacto varchar(10),
gradoCocinero varchar(50),
TipoEmpleado_codigoTipoEmpleado int

);
end $$
Delimiter ;

		    


        #-- Servicios_has_Empleados
Delimiter $$
create procedure sp_Crear_Servicios_has_Empleados()
begin
create table Servicios_has_Empleados(
codigoServicios_has_Empleados int not null primary key auto_increment,
foreign key (Servicios_CodigoServicio) references Servicios(codigoServicio),
foreign key (Empleados_CodigoEmpleado) references Empleados(codigoEmpleado),
Servicios_CodigoServicio int,
Empleados_CodigoEmpleado int,
fechaEvento date,
horaEvento time,
lugarEvento varchar(150)

);
end $$
Delimiter ;
  
#  === ---- LLamar a cada entidad de Crear ---- ===
call sp_Crear_Productos();
call sp_Crear_TipoPlato();
call sp_Crear_TipoEmpleado();
call sp_Crear_Empresas();
call sp_Crear_Presupuesto();
call sp_Crear_Servicios();
call sp_Crear_Platos();
call sp_Crear_Productos_has_Platos();
call sp_Crear_Servicios_has_Platos();
call sp_Crear_Empleados();
call sp_Crear_Servicios_has_Empleados();

#  === Llamar a cada entidad para Listar,Agregar,Actualizar,Eliminar y Buscar ====
   # -- sp_listarProductos
  delimiter $$
create procedure sp_listarProductos()
begin
	select 
	Productos.codigoProducto,
    Productos.nombreProducto,
    Productos.cantidad
    from productos;
end $$
delimiter ;             
  

  
              # -- sp_AgregarProductos
 delimiter $$
create procedure sp_AgregarProductos(in nombreProductoA varchar(150), in cantidadA int)
begin
	insert into Productos(nombreProducto,cantidad) values (nombreProductoA,cantidadA);
end $$
delimiter ;     


        
              # -- sp_ActualizarProductos
delimiter $$
create procedure sp_ActualizarProductos(in  nombreProductoA varchar(150),in cantidadA int,in vaA int)
begin
	update Productos set nombreProducto=nombreProductoA, cantidad=cantidadA  where  codigoProducto=vaA;
end $$
delimiter ;    

          
              # -- sp_EliminarProductos
delimiter $$
create procedure sp_EliminarProductos(in vaA int)
begin
	delete from Productos where codigoProducto=vaA;
end $$
delimiter ;         

     
              # -- sp_BuscarProductos
  delimiter $$
create procedure sp_BuscarProductos(in vaA int)
begin
select 
	Productos.codigoProducto,
    Productos.nombreProducto,
    Productos.cantidad
    from productos
 where codigoProducto=vaA;
end $$
delimiter ;             
 


  # -- sp_listarTipoPlato
  delimiter $$
create procedure sp_listarTipoPlato()
begin
    select
    TipoPlato.codigoTipoPlato,
    TipoPlato.descripcion
	from TipoPlato;
end $$
delimiter ;       

              # -- sp_AgregarTipoPlato
 delimiter $$
create procedure sp_AgregarTipoPlato(in descripcionA varchar(100))
begin
	insert into TipoPlato(descripcion) values (descripcionA);
end $$
delimiter ; 

            
              # -- sp_ActualizarTipoPlato
delimiter $$
create procedure sp_ActualizarTipoPlato(in  descripcionA varchar(100),in vaA int)
begin
	update TipoPlato set descripcion=descripcionA  where  codigoTipoPlato=vaA;
end $$
delimiter ;    

          
              # -- sp_EliminarTipoPlato
delimiter $$
create procedure sp_EliminarTipoPlato(in vaA int)
begin
	delete from TipoPlato where codigoTipoPlato=vaA;
end $$
delimiter ;     

         
              # -- sp_BuscarTipoPlato
delimiter $$
create procedure sp_BuscarTipoPlato(in vaA int)
begin
	 select
    TipoPlato.codigoTipoPlato,
    TipoPlato.descripcion
    from TipoPlato
    where codigoTipoPlato=vaA;
end $$
delimiter ;             




  # -- sp_listarTipoEmpleado
  delimiter $$
create procedure sp_listarTipoEmpleado()
begin
	select
    TipoEmpleado.codigoTipoEmpleado,
    TipoEmpleado.descripcion
    from TipoEmpleado;
end $$
delimiter ;        




              # -- sp_AgregarTipoEmpleado
 delimiter $$
create procedure sp_AgregarTipoEmpleado(in descripcionA varchar(100))
begin
	insert into TipoEmpleado(descripcion) values (descripcionA);
end $$
delimiter ;             


              # -- sp_ActualizarTipoEmpleado
delimiter $$
create procedure sp_ActualizarTipoEmpleado(in  descripcionA varchar(100),in vaA int)
begin
	update TipoEmpleado set descripcion=descripcionA  where  codigoTipoEmpleado=vaA;
end $$
delimiter ;              


              # -- sp_EliminarTipoEmpleado
delimiter $$
create procedure sp_EliminarTipoEmpleado(in vaA int)
begin
	delete from TipoEmpleado where codigoTipoEmpleado=vaA;
end $$
delimiter ;              


              # -- sp_BuscarTipoEmpleado
  delimiter $$
create procedure sp_BuscarTipoEmpleado(in vaA int)
begin
	select
    TipoEmpleado.codigoTipoEmpleado,
    TipoEmpleado.descripcion
    from TipoEmpleado
    where codigoTipoEmpleado=vaA;
end $$
delimiter ;             


   # -- sp_listarEmpresas
  delimiter $$
create procedure sp_listarEmpresas()
begin
	select 
    Empresas.codigoEmpresa,
    Empresas.nombreEmpresa,
    Empresas.direccion,
    Empresas.telefono
    from Empresas;
end $$
delimiter ;        


              # -- sp_AgregarEmpresas
 delimiter $$
create procedure sp_AgregarEmpresas(in nombreEmpresaA varchar(150), in direccionA varchar(150), in telefonoA varchar(10))
begin
	insert into Empresas(nombreEmpresa,direccion,telefono) values (nombreEmpresaA,direccionA,telefonoA);
end $$
delimiter ;           

  
              # -- sp_ActualizarEmpresas
delimiter $$
create procedure sp_ActualizarEmpresas(in nombreEmpresaA varchar(150), in direccionA varchar(150), in telefonoA varchar(10) ,in vaA int)
begin
	update Empresas set nombreEmpresa=nombreEmpresaA,direccion=direccionA , telefono=telefonoA   where  codigoEmpresa=vaA;
end $$
delimiter ;   

           
              # -- sp_EliminarEmpresa
delimiter $$
create procedure sp_EliminarEmpresa(in vaA int)
begin
	delete from Empresas where codigoEmpresa=vaA;
end $$
delimiter ;   

           
              # -- sp_BuscarEmpresa
  delimiter $$
create procedure sp_BuscarEmpresa(in vaA int)
begin
	select 
    Empresas.codigoEmpresa,
    Empresas.nombreEmpresa,
    Empresas.direccion,
    Empresas.telefono
    from Empresas
    where codigoEmpresa=vaA;
end $$
delimiter ;          


      # -- sp_listarPresupuesto
  delimiter $$
create procedure sp_listarPresupuesto()
begin
	select 
    Presupuesto.codigoPresupuesto,
    Presupuesto.fechaSolicitud,
    Presupuesto.cantidadPresupuesto,
    Presupuesto.Empresas_codigoEmpresa
    from Presupuesto;
end $$
delimiter ;        

              # -- sp_AgregarPresupuesto
 delimiter $$
create procedure sp_AgregarPresupuesto(in fechaSolicitudA date, in cantidadPresupuestoA decimal (10,2) ,in Empresas_codigoEmpresaA int)
begin
	insert into Presupuesto(fechaSolicitud,cantidadPresupuesto,Empresas_codigoEmpresa) values (fechaSolicitudA,cantidadPresupuestoA,Empresas_codigoEmpresaA);
end $$
delimiter ;        

     
              # -- sp_ActualizarPresupuesto
delimiter $$
create procedure sp_ActualizarPresupuestos(in fechaSolicitudA date, in cantidadPresupuestoA decimal (10,2),in Empresas_codigoEmpresaA int ,in vaA int)
begin
	update Presupuesto set fechaSolicitud=fechaSolicitudA, cantidadPresupuesto=cantidadPresupuestoA , Empresas_codigoEmpresa=Empresas_codigoEmpresaA  
    where codigoPresupuesto =vaA;
end $$
delimiter ;       

       
              # -- sp_EliminarPresupuesto
delimiter $$
create procedure sp_EliminarPresupuesto(in vaA int)
begin
	delete from Presupuesto where codigoPresupuesto=vaA;
end $$
delimiter ;       

       
              # -- sp_BuscarPresupuesto
  delimiter $$
create procedure sp_BuscarPresupuesto(in vaA int)
begin
	select 
    Presupuesto.codigoPresupuesto,
    Presupuesto.fechaSolicitud,
    Presupuesto.cantidadPresupuesto,
    Presupuesto.Empresas_codigoEmpresa
    from Presupuesto
    where codigoPresupuesto=vaA;
end $$
delimiter ;            



                # -- sp_listarServicios
delimiter $$
create procedure sp_listarServicios()
begin
	select 
    Servicios.codigoServicio,
    Servicios.fechaServicio,
    Servicios.tipoServicio,
    Servicios.horaServicio,
    Servicios.lugarServicio,
    Servicios.telefonoContacto,
    Servicios.Empresas_CodigoEmpresa
    from Servicios;
end $$
delimiter ;        


              # -- sp_AgregarServicios
 delimiter $$
create procedure sp_AgregarServicios(in fechaServicioA date, in tipoServicioA varchar(100), in horaServicioA time, lugarServicioA varchar(100), in telefonoContactoA varchar(10), Empresas_CodigoEmpresaA int)
begin
	insert into Servicios(fechaServicio,tipoServicio,horaServicio,lugarServicio,telefonoContacto,Empresas_codigoEmpresa) values 
    (fechaServicioA,  tipoServicioA, horaServicioA,  lugarServicioA,  telefonoContactoA,  Empresas_CodigoEmpresaA);
end $$
delimiter ;

             
              # -- sp_ActualizarServicios
delimiter $$
create procedure sp_ActualizarServicios(in fechaServicioA date, in tipoServicioA varchar(100), in horaServicioA time, lugarServicioA varchar(100), in telefonoContactoA varchar(10), Empresas_CodigoEmpresaA int  ,in vaA int)
begin
	update Servicios set  fechaServicio=fechaServicioA, tipoServicio=tipoServicioA,  horaServicio=horaServicioA, lugarServicio=lugarServicioA, telefonoContacto=telefonoContactoA, Empresas_CodigoEmpresa=Empresas_CodigoEmpresaA
    where codigoServicio =vaA;
end $$
delimiter ;

              
              # -- sp_EliminarServicios
delimiter $$
create procedure sp_EliminarServicios(in vaA int)
begin
	delete from Servicios where codigoServicio=vaA;
end $$
delimiter ;

              
              # -- sp_BuscarServicios
  delimiter $$
create procedure sp_BuscarServicios(in vaA int)
begin
    select 
	Servicios.codigoServicio,
    Servicios.fechaServicio,
    Servicios.tipoServicio,
    Servicios.horaServicio,
    Servicios.lugarServicio,
    Servicios.telefonoContacto,
    Servicios.Empresas_CodigoEmpresa
    from Servicios
    where codigoServicio=vaA;
end $$
delimiter ;     



   # -- sp_listarPlatos
  delimiter $$
create procedure sp_listarPlatos()
begin
	select 
    Platos.codigoPlato,
    Platos.cantidad,
    Platos.nombrePlato,
    Platos.descripcionPlato,
    Platos.precioPlato,
    Platos.TipoPlato_codigoTipoPlato
    from Platos;
end $$
delimiter ;        


              # -- sp_AgregarPlatos
 delimiter $$
create procedure sp_AgregarPlatos(in cantidadA int, nombrePlatoA varchar(50), descripcionPlatoA varchar(150), precioPlatoA decimal(10,2), TipoPlato_codigoPlatoA int)
begin
	insert into Platos(cantidad,nombrePlato, descripcionPlato,precioPlato,TipoPlato_codigoTipoPlato) values 
    (cantidadA,nombrePlatoA,descripcionPlatoA,precioPlatoA,TipoPlato_codigoPlatoA);
end $$
delimiter ;

             
              # -- sp_ActualizarPlatos
delimiter $$
create procedure sp_ActualizarPlatos(in cantidadA int, in  nombrePlatoA varchar(50),in  descripcionPlatoA varchar(150),in  precioPlatoA decimal(10,2),in TipoPlato_codigoPlatoA int , in vaA int)
begin
	update Platos set cantidad=cantidadA, nombrePlato=nombrePlatoA, descripcionPlato=descripcionPlatoA, precioPlato=precioPlatoA, TipoPlato_codigoTipoPlato=TipoPlato_codigoPlatoA    
    where codigoPlato=vaA;
end $$
delimiter ;       

            
              # -- sp_EliminarPlatos
delimiter $$
create procedure sp_EliminarPlatos( vaA int)
begin
	delete from Platos where codigoPlato=vaA;
end $$
delimiter ;              


              # -- sp_BuscarPlatos
  delimiter $$
create procedure sp_BuscarPlatos(in vaA int)
begin
	select 
    Platos.codigoPlato,
    Platos.cantidad,
    Platos.nombrePlato,
    Platos.descripcionPlato,
    Platos.precioPlato,
    Platos.TipoPlato_codigoTipoPlato
    from Platos
    where codigoPlato=vaA;
end $$
delimiter ;             



   # -- sp_listarProductos_has_Platos
  delimiter $$
create procedure sp_listarProductos_has_Platos()
begin
	select 
    Productos_has_Platos.codigo_has_Platos,
    Productos_has_Platos.Productos_CodigoProductos,
    Productos_has_Platos.Platos_CodigoPlato
    from Productos_has_Platos;
end $$
delimiter ;    


              # -- sp_AgregarProductos_has_Platos
 delimiter $$
create procedure sp_AgregarProductos_has_Platos(in Productos_CodigoProductosA int, in  Platos_CodigoPlatoA int)
begin
	insert into Productos_has_Platos(Productos_CodigoProductos,Platos_CodigoPlato) values 
    (Productos_CodigoProductosA,Platos_CodigoPlatoA);
end $$
delimiter ;

             
              # -- sp_ActualizarProductos_has_Platos
 delimiter $$
create procedure sp_ActualizarProducto_has_Platos(in  Productos_CodigoProductosA int, in  Platos_CodigoPlatoA int , in vaA int)
begin
	update Productos_has_Platos set Productos_CodigoProductos= Productos_CodigoProductosA,Platos_CodigoPlato=Platos_CodigoPlatoA
    where codigo_has_Platos=vaA;
end $$
delimiter ;       

            
              # -- sp_EliminarProductos_has_Platos
delimiter $$
create procedure sp_EliminarProductos_has_Platos(in  vaA int)
begin
	delete from Productos_has_Platos where  codigo_has_Platos=vaA;
end $$
delimiter ;     

         
              # -- sp_BuscarProductos_has_Platos
  delimiter $$
create procedure sp_BuscarProductos_has_Platos(in vaA int)
begin
	select 
    Productos_has_Platos.codigo_has_Platos,
    Productos_has_Platos.Productos_CodigoProductos,
    Productos_has_Platos.Platos_CodigoPlato
    from Productos_has_Platos
    where codigo_has_Platos=vaA;
end $$
delimiter ;       


    # -- sp_listarServicios_has_Platos
  delimiter $$
create procedure sp_listarServicios_has_Platos()
begin
	select 
    Servicios_has_Platos.codigoServicios_has_Platos,
	Servicios_has_Platos.Servicios_CodigoServicio,
	Servicios_has_Platos.Platos_CodigoPlato
    from Servicios_has_Platos;
end $$
delimiter ;        



              # -- sp_AgregarServicios_has_Platos
 delimiter $$
create procedure sp_AgregarServicios_has_Platos(in Servicios_CodigoServicioA int, Platos_CodigoPlatoA int)
begin
	insert into Servicios_has_Platos(Servicios_CodigoServicio,Platos_CodigoPlato) values 
    (Servicios_CodigoServicioA, Platos_CodigoPlatoA);
end $$
delimiter ;

             
              # -- sp_ActualizarServicios_has_Platos
 delimiter $$
create procedure sp_ActualizarServicios_has_Platos(in  Servicios_CodigoServicioA int, Platos_CodigoPlatoA int  , in vaA int)
begin
	update Servicios_has_Platos set Servicios_CodigoServicio=Servicios_CodigoServicioA, Platos_CodigoPlato=Platos_CodigoPlatoA
    where codigoServicios_has_Platos=vaA;
end $$
delimiter ;         


              # -- sp_EliminarServicios_has_Platos
delimiter $$
create procedure sp_EliminarServicios_has_Platos(vaA int)
begin
	delete from Servicios_has_Platos where codigoServicios_has_Platos=vaA;
end $$
delimiter ;            

  
              # -- sp_BuscarServicios_has_Platos
  delimiter $$
create procedure sp_BuscarServicios_has_Platos(in vaA int)
begin
	select 
    Servicios_has_Platos.codigoServicios_has_Platos,
	Servicios_has_Platos.Servicios_CodigoServicio,
	Servicios_has_Platos.Platos_CodigoPlato
    from Servicios_has_Platos
    where codigoServicios_has_Platos=vaA;
end $$
delimiter ;    


  # -- sp_listarEmpleados
  delimiter $$
create procedure sp_listarEmpleados()
begin
	select 
    Empleados.codigoEmpleado,
    Empleados.numeroEmpleado,
    Empleados.apellidosEmpleado,
    Empleados.nombresEmpleado,
    Empleados.direccionEmpleado,
    Empleados.telefonoContacto,
    Empleados.gradoCocinero,
    Empleados.TipoEmpleado_codigoTipoEmpleado
    from Empleados;
end $$
delimiter ;  



              # -- sp_AgregarEmpleados
 delimiter $$
create procedure sp_AgregarEmpleados(in numeroEmpleadoA int, in apellidosEmpleadoA varchar(150),nombresEmpleadoA varchar(150), direccionEmpleadoA varchar(150), telefonoContactoA varchar(10),
                                         gradoCocineroA varchar(50),TipoEmpleado_codigoTipoEmpleadoA int)
begin
	insert into Empleados(numeroEmpleado,apellidosEmpleado,nombresEmpleado,direccionEmpleado,telefonoContacto,gradoCocinero,TipoEmpleado_codigoTipoEmpleado) values 
    (numeroEmpleadoA,apellidosEmpleadoA,nombresEmpleadoA,direccionEmpleadoA,telefonoContactoA, gradoCocineroA,TipoEmpleado_codigoTipoEmpleadoA);
end $$
delimiter ;          

   
              # -- sp_ActualizarEmpleados
 delimiter $$
create procedure sp_ActualizarEmpleados(in  numeroEmpleadoA int, in apellidosEmpleadoA varchar(150),nombresEmpleadoA varchar(150), direccionEmpleadoA varchar(150), telefonoContactoA varchar(10),
                                         gradoCocineroA varchar(50),TipoEmpleado_codigoTipoEmpleadoA int  , in vaA int)
begin
	update Empleados set numeroEmpleado=numeroEmpleadoA, apellidosEmpleado=apellidosEmpleadoA, nombresEmpleado=nombresEmpleadoA,  direccionEmpleado=direccionEmpleadoA, telefonoContacto=telefonoContactoA,
            gradoCocinero= gradoCocineroA,  TipoEmpleado_codigoTipoEmpleado=TipoEmpleado_codigoTipoEmpleadoA
    where codigoEmpleado=vaA;
end $$
delimiter ;      

    
              # -- sp_EliminarEmpleados
delimiter $$
create procedure sp_EliminarEmpleados( vaA int)
begin
	delete from Empleados  where codigoEmpleado=vaA;
end $$
delimiter ;             

 
              # -- sp_BuscarEmpleados
  delimiter $$
create procedure sp_BuscarEmpleados(in vaA int)
begin
	select 
    Empleados.codigoEmpleado,
    Empleados.numeroEmpleado,
    Empleados.apellidosEmpleado,
    Empleados.nombresEmpleado,
    Empleados.direccionEmpleado,
    Empleados.telefonoContacto,
    Empleados.gradoCocinero,
    Empleados.TipoEmpleado_codigoTipoEmpleado
    from Empleados
    where codigoEmpleado=vaA;
end $$
delimiter ;    


   # -- sp_listarServicios_has_Empleados
  delimiter $$
create procedure sp_listarServicios_has_Empleados()
begin
	select 
    Servicios_has_Empleados.codigoServicios_has_Empleados,
    Servicios_has_Empleados.Servicios_CodigoServicio,
    Servicios_has_Empleados.Empleados_CodigoEmpleado,
    Servicios_has_Empleados.fechaEvento,
    Servicios_has_Empleados.horaEvento,
    Servicios_has_Empleados.lugarEvento
    from Servicios_has_Empleados;
end $$
delimiter ;        

              # -- sp_AgregarServicios_has_Empleados
 delimiter $$
create procedure sp_AgregarServicios_has_Empleados(in Servicios_CodigoServicioA int, Empleados_CodigoEmpleadoA int, fechaEventoA date, horaEventoA time, lugarEventoA varchar(150)  )
begin
	insert into Servicios_has_Empleados(Servicios_CodigoServicio,Empleados_CodigoEmpleado,fechaEvento,horaEvento,lugarEvento) values 
    (Servicios_CodigoServicioA,Empleados_CodigoEmpleadoA,fechaEventoA,horaEventoA,lugarEventoA);
end $$
delimiter ;        

     
              # -- sp_ActualizarServicio_has_Empleados
 delimiter $$
create procedure sp_ActualizarServicios_has_Empleados(in Servicios_CodigoServicioA int, Empleados_CodigoEmpleadoA int, fechaEventoA date, horaEventoA time, lugarEventoA varchar(150)  , in vaA int)
begin
	update Servicios_has_Empleados  set Servicios_CodigoServicio=Servicios_CodigoServicioA, Empleados_CodigoEmpleado=Empleados_CodigoEmpleadoA, fechaEvento=fechaEventoA, horaEvento=horaEventoA, lugarEvento=lugarEventoA
    where codigoServicios_has_Empleados=vaA;
end $$
delimiter ;          


              # -- sp_EliminarServicios_has_Empleados
delimiter $$
create procedure sp_EliminarServicios_has_Empleados( in vaA int)
begin
	delete from  Servicios_has_Empleados  where codigoServicios_has_Empleados=vaA;
end $$
delimiter ;     

         
              # -- sp_BuscarServicios_has_Empleados
  delimiter $$
create procedure sp_BuscarServicios_has_Empleados(in vaA int)
begin
	select 
    Servicios_has_Empleados.codigoServicios_has_Empleados,
    Servicios_has_Empleados.Servicios_CodigoServicio,
    Servicios_has_Empleados.Empleados_CodigoEmpleado,
    Servicios_has_Empleados.fechaEvento,
    Servicios_has_Empleados.horaEvento,
    Servicios_has_Empleados.lugarEvento
    from Servicios_has_Empleados
    where codigoServicios_has_Empleados=vaA;
end $$
delimiter ;    



#     =---  Ingreseo De Informacion ---=
     # = --- Productos --- =

call sp_AgregarProductos("Pan",1000);
call sp_AgregarProductos("Trigo",150);
call sp_AgregarProductos("Lece",130);
call sp_AgregarProductos("Huevos",130);
call sp_AgregarProductos("Jugos",130);



    #  = --- Tipo Plato --- =

call sp_AgregarTipoPlato("Plato Fuerte");
call sp_AgregarTipoPlato("Plato Normal");
call sp_AgregarTipoPlato("Plato Especial");
call sp_AgregarTipoPlato("Plato Boda");
call sp_AgregarTipoPlato("Plato XV Años");

    #  = --- Tipo Empleado--- =

call sp_AgregarTipoEmpleado("Jefe");
call sp_AgregarTipoEmpleado("Gerente");
call sp_AgregarTipoEmpleado("Normal");
call sp_AgregarTipoEmpleado("Socio");
call sp_AgregarTipoEmpleado("Avanzado");




    #  = --- Empresas --- =

call sp_AgregarEmpresas("Sociedad Anonima 2","47 calle","76779444");
call sp_AgregarEmpresas("Sociedad Anonima 3","14 calle","64116464");
call sp_AgregarEmpresas("Camarones S.A","15 calle","20242420");
call sp_AgregarEmpresas("Sopas S.A","2 calle","31646416");
call sp_AgregarEmpresas("Carros S.A","1 calle","1740");



     # = --- Presupuesto --- =

call sp_AgregarPresupuesto("2020/02/09",14.00,1);
call sp_AgregarPresupuesto("2020/06/09",200.00,2);
call sp_AgregarPresupuesto("2020/02/09",20.00,3);
call sp_AgregarPresupuesto("2011/02/01",10.00,4);
call sp_AgregarPresupuesto("2012/01/01",12.00,5);


     # = -- Servicios---=

call sp_AgregarServicios("2020/02/08","Profesional","15:50:00","Capital","616161",1);
call sp_AgregarServicios("2020/06/15","Experto","15:50:00","Capital","616161",2);
call sp_AgregarServicios("2010/06/10","Grande","12:50:00","Capital","786494",1);
call sp_AgregarServicios("2009/05/12","Mediano","11:50:00","Capital","354849",2);
call sp_AgregarServicios("2008/01/11","Pequeño","14:50:00","Capital","494949",1);




     # = --- Platos --- =

call sp_AgregarPlatos(150,"Plato Normal","Pequeño",14.20,2);
call sp_AgregarPlatos(100,"Plato Especial","Grande",15.2,4);
call sp_AgregarPlatos(125,"Plato Ordenado","Ordenado",150.2,2);
call sp_AgregarPlatos(11,"Plato Grande","Grande",115.2,5);
call sp_AgregarPlatos(120,"Plato Mediano","Mediano",215.2,4);
call sp_AgregarPlatos(150,"Plato Familiar","Grande",175.2,2);



    #--  Producto_has_Platos

call sp_AgregarProductos_has_Platos(1,1);
call sp_AgregarProductos_has_Platos(2,4);
call sp_AgregarProductos_has_Platos(3,5);
call sp_AgregarProductos_has_Platos(4,4);
call sp_AgregarProductos_has_Platos(5,1);



    # =--- Servicios_has_Platos --- =

call sp_AgregarServicios_has_Platos(1,1);
call sp_AgregarServicios_has_Platos(2,2);
call sp_AgregarServicios_has_Platos(3,3);
call sp_AgregarServicios_has_Platos(4,4);
call sp_AgregarServicios_has_Platos(5,5);


    # = ---Empleados--- = 

call sp_AgregarEmpleados(56464,"Battel","Juan","15 calle","464464","Avanzado",2);
call sp_AgregarEmpleados(456464,"Jimenez Arriola","Pollito","16 calle","464464","Profesional",1);
call sp_AgregarEmpleados(56464,"Areano","Joaquin","11 calle","64464","Avanzado",1);
call sp_AgregarEmpleados(78464,"Guzman","Jose","16 calle","464464","Profesional",2);
call sp_AgregarEmpleados(464949,"Juan","Julio","11 calle","9864464","Medio",1);


   # = --- Servicios_has_Empleados--- =

call sp_AgregarServicios_has_Empleados(1,2,"2020/02/02","15:00:00","Capital");
call sp_AgregarServicios_has_Empleados(3,3,"2020/02/02","02:00:00","Capital");
call sp_AgregarServicios_has_Empleados(1,2,"2020/01/02","09:00:00","Capital");
call sp_AgregarServicios_has_Empleados(3,3,"2020/03/02","10:00:00","Capital");
call sp_AgregarServicios_has_Empleados(1,2,"2020/05/02","11:00:00","Capital");


   # = --- Inner Joins --- =
Delimiter $$
create procedure sp_ListarReporte(in Empresas_codigoEmpresa int)
Begin
 select * from Empresas E inner join Presupuesto P on
 E.codigoEmpresa = P.Empresas_codigoEmpresa
 inner join Servicios S on
 P.Empresas_codigoEmpresa = S.Empresas_CodigoEmpresa
 where E.codigoEmpresa = Empresas_codigoEmpresa group by s.tipoServicio HAVING COUNT(*) > 1;
 End$$
Delimiter ;
call sp_ListarReporte(1);



Delimiter $$
create procedure sp_ListarReporteServicios(in codServicio int)
begin 
  select  s.tipoServicio, s.codigoServicio, s.lugarServicio, s.fechaServicio, p.codigoPlato, p.nombrePlato, p.cantidad, p.TipoPlato_codigoTipoPlato as TipoPlato,
  pt.codigoProducto, pt.nombreProducto
  from Servicios as s
  inner join servicios_has_platos shp on S.codigoServicio= shp.Servicios_CodigoServicio
   inner join Platos P on P.codigoPlato=  shp.Platos_CodigoPlato
    inner join Productos_has_Platos php on php.Platos_CodigoPlato = p.codigoPlato
     inner join Productos pt on pt.codigoProducto= php.Productos_CodigoProductos  where s.codigoServicio = codigoServicio group by p.codigoPlato;
      end $$
      Delimiter ;
       
call sp_ListarReporteServicios(1);