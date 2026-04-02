# 📋 GlobalExceptionHandler - Guía Completa

## ¿Qué es GlobalExceptionHandler?

Es un **controlador centralizado de excepciones** que intercepta todas las excepciones lanzadas en los controladores REST y las convierte en respuestas HTTP estandarizadas.

---

## 🏗️ Arquitectura y Flujo

```
CandidatoServicio (Lanza excepción)
        ↓
CandidatoControlador (No captura)
        ↓
GlobalExceptionHandler (INTERCEPTA)
        ↓
ResponseModel (Respuesta estandarizada)
        ↓
Cliente (JSON con error formateado)
```

---

## 📌 Cómo funciona

### 1. **@RestControllerAdvice**
- Annotation que indica que este bean actúa como handler global
- Intercepta excepciones de TODOS los controladores REST

### 2. **@ExceptionHandler**
- Define qué método maneja qué tipo de excepción
- Se ejecuta automáticamente cuando se lanza esa excepción

### 3. **Excepciones manejadas**

| Excepción | Código HTTP | Caso de uso |
|-----------|------------|-----------|
| `ReclutamientoNotFoundException` | 404 | Recurso no encontrado |
| `ReclutamientoBusinessException` | 400 | Violación de regla de negocio |
| `MethodArgumentNotValidException` | 400 | Validación de campos (@Valid) |
| `Exception` (genérica) | 500 | Error inesperado |

---

## 💡 Mejoras implementadas en CandidatoServicio

### Antes (Problemático ❌)
```java
public ResponseModel<CandidatoModel> obtenerPorId(Long id) {
    var candidato = candidatoRepositorio.findById(id)
            .orElseThrow(() -> new RuntimeException("Candidato no encontrado con id: " + id));
    // ...
}
```

**Problemas:**
- ❌ Usa `RuntimeException` genérica
- ❌ GlobalExceptionHandler devuelve 500 (error interno)
- ❌ Sin validación de entrada

### Después (Mejorado ✅)
```java
public ResponseModel<CandidatoModel> obtenerPorId(Long id) {
    // Validación de entrada
    if (id == null || id <= 0) {
        throw new ReclutamientoBusinessException("El ID debe ser positivo");
    }
    
    // Excepción específica
    var candidato = candidatoRepositorio.findById(id)
            .orElseThrow(() -> new ReclutamientoNotFoundException("Candidato no encontrado"));
    
    log.info("Candidato encontrado: {}", id);
    
    return ResponseModel.<CandidatoModel>builder()
            .message("Candidato obtenido correctamente")
            .data(CandidatoModel.FN_ENTITY_TO_MODEL.apply(candidato))
            .build();
}
```

**Mejoras:**
- ✅ Validación de parámetros de entrada
- ✅ Excepción específica → Código HTTP correcto (404)
- ✅ GlobalExceptionHandler captura y formatea automáticamente
- ✅ Logging para auditoría

---

## 🔄 Flujo de una solicitud fallida

### Ejemplo 1: Candidato no encontrado
```
GET /api/candidatos/999
        ↓
CandidatoServicio.obtenerPorId(999)
        ↓
Lanza: new ReclutamientoNotFoundException("Candidato no encontrado con id: 999")
        ↓
GlobalExceptionHandler.handleNotFoundException()
        ↓
HTTP 404 JSON:
{
    "message": "Candidato no encontrado con id: 999",
    "data": null
}
```

### Ejemplo 2: ID inválido
```
GET /api/candidatos/-5
        ↓
CandidatoServicio.obtenerPorId(-5)
        ↓
Valida: id <= 0
        ↓
Lanza: new ReclutamientoBusinessException("El ID debe ser positivo")
        ↓
GlobalExceptionHandler.handleBusinessException()
        ↓
HTTP 400 JSON:
{
    "message": "El ID debe ser positivo",
    "data": null
}
```

---

## 📝 Buenas prácticas implementadas

### 1. **Excepción específica según el caso**
```java
// ❌ MAL
throw new RuntimeException("Error");

// ✅ BIEN
throw new ReclutamientoNotFoundException("Candidato no encontrado");
throw new ReclutamientoBusinessException("Validación fallida");
```

### 2. **Validación en el Servicio**
```java
// ❌ MAL (Dejar que falle la BD)
candidatoRepositorio.save(createModel);

// ✅ BIEN (Validar primero)
if (createModel.getEmail() == null || createModel.getEmail().isBlank()) {
    throw new ReclutamientoBusinessException("Email obligatorio");
}
candidatoRepositorio.save(createModel);
```

### 3. **Logging adecuado**
```java
log.info("Candidato encontrado con id: {}", id);      // Información
log.warn("Recurso no encontrado: {}", ex.getMessage()); // GlobalExceptionHandler
log.error("Error interno: {}", ex.getMessage(), ex);    // GlobalExceptionHandler
```

### 4. **Respuestas estandarizadas**
```java
// Todas las respuestas usan ResponseModel
ResponseModel.<CandidatoModel>builder()
    .message("Mensaje descriptivo")
    .data(datos)
    .build()
```

---

## 🎯 Ventajas del patrón GlobalExceptionHandler

| Ventaja | Descripción |
|---------|-----------|
| **Centralización** | Un único lugar para manejar excepciones |
| **Consistencia** | Todas las respuestas de error tienen el mismo formato |
| **HTTP correcto** | Códigos HTTP apropiados (404, 400, 500) |
| **Seguridad** | No expone detalles internos del sistema |
| **Mantenimiento** | Cambios centralizados, no en cada controlador |
| **Logging automático** | Todas las excepciones se registran |

---

## 📌 Resumen

- **GlobalExceptionHandler**: Interceptor centralizado de excepciones
- **Usa excepciones específicas** en lugar de Exception genérica
- **Valida en el servicio** antes de operaciones críticas
- **Devuelve códigos HTTP correctos** (404, 400, 500)
- **Estandariza respuestas** con ResponseModel
- **Beneficio**: Frontend siempre recibe respuestas consistentes y predecibles


