# Documentação da API de Login

## Visão Geral
Sistema de autenticação de usuários implementado no Gerenciador de Filas Online.

## Endpoints Disponíveis

### 1. Login de Usuário
**POST** `/api/auth/login`

Realiza o login de um usuário no sistema.

**Request Body:**
```json
{
    "email": "usuario@exemplo.com",
    "senha": "senha123"
}
```

**Response (Sucesso - 200):**
```json
{
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "nome": "João Silva",
    "email": "usuario@exemplo.com",
    "token": "token_123e4567-e89b-12d3-a456-426614174000_1703123456789",
    "tipoToken": "Bearer"
}
```

**Response (Erro - 401):**
```json
"Erro na autenticação: Usuário não encontrado ou inativo"
```

### 2. Registro de Usuário
**POST** `/api/auth/registro`

Registra um novo usuário no sistema.

**Request Body:**
```json
{
    "nome": "João Silva",
    "email": "joao@exemplo.com",
    "senha": "senha123"
}
```

**Response (Sucesso - 201):**
```json
"Usuário registrado com sucesso! ID: 123e4567-e89b-12d3-a456-426614174000"
```

**Response (Erro - 400):**
```json
"Erro no registro: Email já cadastrado"
```

### 3. Buscar Usuário por Email
**GET** `/api/auth/usuario/{email}`

Busca um usuário pelo email.

**Response (Sucesso - 200):**
```json
{
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "nome": "João Silva",
    "email": "joao@exemplo.com",
    "senha": "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi",
    "ativo": true
}
```

## Estrutura do Banco de Dados

### Tabela: usuarios
```sql
CREATE TABLE usuarios (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## Usuário Administrador Padrão

O sistema inclui um usuário administrador padrão:
- **Email:** admin@gerenciador.com
- **Senha:** admin123

## Segurança

- Senhas são criptografadas usando BCrypt
- Tokens são gerados de forma simples (recomenda-se implementar JWT em produção)
- Endpoints de autenticação são públicos
- Demais endpoints requerem autenticação

## Validações

### LoginRequestDTO
- `email`: Obrigatório, formato de email válido
- `senha`: Obrigatória

### RegistroUsuarioDTO
- `nome`: Obrigatório, entre 2 e 100 caracteres
- `email`: Obrigatório, formato de email válido
- `senha`: Obrigatória, mínimo 6 caracteres

## Exemplos de Uso com cURL

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@gerenciador.com",
    "senha": "admin123"
  }'
```

### Registro
```bash
curl -X POST http://localhost:8080/api/auth/registro \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maria Santos",
    "email": "maria@exemplo.com",
    "senha": "minhasenha123"
  }'
```

### Buscar Usuário
```bash
curl -X GET http://localhost:8080/api/auth/usuario/admin@gerenciador.com
```
