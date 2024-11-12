Desarrollador: Facundo Torres

Descripción del Proyecto:

Este es un proyecto académico para el desarrollo de una aplicación de comercio electrónico (e-commerce). La aplicación incluye las siguientes características:

Pantallas de Onboarding y Splash para una experiencia de bienvenida.
Funcionalidades de inicio de sesión y registro (LogIn/SignUp).
Página de inicio (HomePage) donde se pueden ver todos los productos y una barra de búsqueda para filtrar productos por categoría o palabra clave.
Opciones para agregar productos al carrito de compras o a la lista de favoritos. Desde favoritos, los productos se pueden transferir al carrito.
Checkout que permite seleccionar métodos de pago, ubicación de envío y otros detalles de compra.
Tecnologías utilizadas:

Lenguaje de programación: Kotlin
Framework de UI: Jetpack Compose (Material 3)
Integración de red: Retrofit
Almacenamiento y autenticación: Firebase (Firebase Auth, Firestore)
Carga de imágenes: Coil
Versión de Android Studio: Koala





 > ¿Qué tipo de arquitectura usaron y por qué? ¿Podría mejorarla?

<p align="justify">
La arquitectura que implementé es principalmente MVVM (Model-View-ViewModel), ya que permite una clara separación de responsabilidades. En los ViewModels manejo la lógica de negocio y la interacción con los datos, mientras que las Screens se encargan de la presentación visual. Los repositorios actúan como intermediarios entre los datos (API, Firebase) y los ViewModels, facilitando las pruebas unitarias y el desacoplamiento.

Para mejorar, podría incorporar un sistema de inyección de dependencias como Hilt para gestionar mejor las dependencias entre los componentes y optimizar el mantenimiento y escalabilidad. Además, añadiría más modularización para aislar las responsabilidades, como por ejemplo separar mejor los componentes UI reutilizables.
</p>

> ¿Tuvieron objetos stateful y stateless? ¿Cómo definen la elección de los mismos?

<p align="justify">
Sí, utilicé ambos tipos de componentes. Los stateful los implementé en las pantallas que manejan su propio estado, como la CheckoutScreen, donde las interacciones del usuario modifican datos locales (ej. método de entrega, código promocional). Utilizo remember y mutableStateOf para mantener el estado.
Por otro lado, los componentes stateless se usan cuando simplemente reciben datos y no necesitan gestionar un estado interno, como es el caso de CheckoutOptionItem, que solo muestra información sin modificarla.
La elección depende de si el componente necesita manejar datos que cambian dinámicamente o si solo presenta datos ya procesados.
</p>

> ¿Qué mejoras detectan que podrían realizarle a la app? ¿Tendrían side effect si escala el volumen de datos? Comenten al menos 2 cuestiones a refactorizar y tener en cuenta.

<p align="justify">
Una mejora sería implementar el uso más extendido de ViewModelFactorys para garantizar una mejor gestión de dependencias. Esto haría más robusto el código y facilitaría pruebas unitarias.
Otra mejora sería implementar una estrategia de caché en los repositorios, de forma que se reduzcan las llamadas innecesarias a la API y mejorar la experiencia del usuario, especialmente cuando el volumen de datos aumenta.

Efectos secundarios al escalar:

Sin caché, podría aumentar el tiempo de respuesta debido a demasiadas llamadas a la API, afectando el rendimiento.

El manejo de un gran volumen de datos sin optimización podría consumir demasiada memoria, lo que generaría problemas en dispositivos con recursos limitados.

Para refactorizar, propondría mejorar el manejo de estado con StateFlows combinados y optimizar la sincronización de los datos entre la API y una base de datos local.
</p>

> ¿Qué manejo de errores harían? ¿dónde los contemplan a nivel código? Mencionen la estrategia de mapeo que más se adecúe.

<p align="justify">
El manejo de errores está implementado en varias partes del proyecto, principalmente a través de bloques try-catch. Esto lo utilicé en funciones que realizan llamadas a la API, como en el ProductViewModel para cargar productos y categorías. La estrategia que seguí fue asignar los errores a un estado específico de la UI, representado por UiState.Error, lo que me permite mostrar mensajes de error en la interfaz de usuario.

Por ejemplo, en la función loadProducts(), utilizo un bloque try-catch para capturar excepciones durante la obtención de productos desde el repositorio. Si ocurre un error, el estado de la UI cambia a UiState.Error con un mensaje descriptivo, lo cual le indica al usuario que algo salió mal. El mismo enfoque se aplica a la función loadCategories().

Para mejorar este manejo de errores, podría implementar una estrategia de mapeo más robusta utilizando sealed classes para categorizar los errores en tipos específicos, como errores de red, de autenticación o desconocidos. Esto no solo ayudaría a mostrar mensajes más específicos y útiles, sino que también mejoraría la gestión de errores a lo largo de toda la aplicación, brindando una experiencia más clara y controlada para el usuario.
</p>

> En el caso de uso de persistencia para Favoritos, ¿que estrategia sugieren?

<p align="justify">
Actualmente, utilizo una colección general en Firebase para almacenar los favoritos, pero una mejora sería asociar cada usuario registrado con su propia colección de IDs de productos favoritos. Esto permitiría una personalización más clara, mejorar la seguridad y facilitar la escalabilidad. Cada usuario tendría acceso únicamente a sus favoritos, lo que también facilita la gestión de datos en un entorno multiusuario.
</p>

> Si la tendríamos que convertir a Español y conservar el Inglés, qué estrategia utilizarían para extenderla. Y si necesitamos agregar otros idiomas?

<p align="justify">
Utilizaría una estrategia de internacionalización (i18n) mediante archivos de recursos (strings.xml) específicos para cada idioma. Así, para cada idioma que se quiera soportar (español, inglés, francés, etc.), solo sería necesario crear nuevas versiones de estos archivos (values-es, values-fr), permitiendo que la aplicación sea fácilmente extensible a nuevos idiomas.
</p>
