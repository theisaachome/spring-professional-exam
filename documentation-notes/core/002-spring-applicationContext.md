
What is ApplicationContext Interface?
The ApplicationContext is the central interface within a Spring application for providing configuration information to the application. 

The interfaces BeanFactory and ApplicationContext represent the Spring IoC container. Here, BeanFactory is the root interface for accessing the Spring container. It provides basic functionalities for managing beans.

On the other hand, the ApplicationContext is a sub-interface of the BeanFactory. Therefore, it offers all the functionalities of BeanFactory. Furthermore, it provides more enterprise-specific functionalities. 

The important features of ApplicationContext are 
resolving messages
supporting internationalization,
publishing events
application-layer-specific contexts
This is why we use ApplicationContext as the default Spring container.