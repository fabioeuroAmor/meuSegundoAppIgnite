package org.apache.ignite.meuSegundoAppIgnite;

import java.util.Collections;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;

import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;

/**
 * Hello world!
 *
 */
public class App {
    
	
	public static void main(String[] args) throws IgniteException {

		System.out.println("Minha segunda APP ignite!!!");

		/*try {
			
			Ignite ignite = Ignition.start("C:\\AmbienteBB\\pin-ignite\\meuPrimeiroAppIgnite\\config\\client-node-config.xml");			
			ignite.compute().broadcast(() -> System.out.println("Teste"));			
		} catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
		}*/

		
		  // Preparando IgniteConfiguration usando APIs Java //Instanciando o objeto de		 configuração do ignite 
		IgniteConfiguration cfg = new IgniteConfiguration();
		 
		  
		  // O nó será iniciado como um nó do cliente. 
		 //cfg.setClientMode(true);
		  
		  // Classes da lógica Java customizada serão transferidas pela conexão apartir deste aplicativo. 
		  cfg.setPeerClassLoadingEnabled(true);
		  
		  // Configurando um Localizador de IP para garantir que o cliente possa localizar os servidores. 
		  TcpDiscoveryMulticastIpFinder ipFinder = new
		  TcpDiscoveryMulticastIpFinder();
		  ipFinder.setAddresses(Collections.singletonList("127.0.0.1:49500..49520"));
		  cfg.setDiscoverySpi(new TcpDiscoverySpi().setIpFinder(ipFinder));
		  
		  // Iniciando o nó //Ignite ignite =
		  //Ignition.start("C:\\AmbienteBB\\pin-ignite\\meuPrimeiroAppIgnite\\config\\client-node-config.xml");
		  //Ignite ignite = Ignition.start(cfg);
		  
		  Ignite ignite =Ignition.start("C:\\AmbienteBB\\pin-ignite\\meuSegundoAppIgnite\\config\\second-cluster.xml");
		  
		  //Criando um IgniteCache e colocando alguns valores nele.
		  IgniteCache<Integer, String> cache = ignite.getOrCreateCache("myCache");
		  cache.put(1, "Hello 2"); 
		  cache.put(2, "World 2!");
		  cache.put(3, "Terceiro 2 objeto em cache de memória!");
		  
		  System.out.println(">> Criado o cache e adicionado os valores.");
		  
		  // Executando tarefa de computação Java customizada nos nós do servidor.
		  ignite.compute(ignite.cluster().forServers()).broadcast(new RemoteTask());
		  
		  System.out.println(">> A tarefa de computação é executada, verifique a saída nos nós do servidor.");
		 
		 // Disconnect from the cluster. 
		 // ignite.close();
		 

	}
	
}
