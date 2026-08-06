import { useState } from "react";
import { useNavigate } from "react-router";
import { api } from "../api/api";

export default function Login() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("joaojwt@email.com");
  const [senha, setSenha] = useState("123456");
  const [erro, setErro] = useState("");

  async function handleLogin(e) {
    e.preventDefault();
    setErro("");

    try {
      const response = await api.post("/auth/login", {
        email,
        senha,
      });

      console.log("Resposta do login:", response.data);

      localStorage.setItem("token", response.data.token);
      localStorage.setItem("userId", response.data.userId);
      localStorage.setItem("nome", response.data.nome);
      localStorage.setItem("email", response.data.email);

      navigate("/recommendations");
    } catch {
      setErro("Email ou senha inválidos.");
    }
  }

  return (
    <main style={{ maxWidth: 400, margin: "80px auto", fontFamily: "Arial" }}>
      <h1>Entrar</h1>

      <form onSubmit={handleLogin}>
        <input
          style={{ width: "100%", padding: 10, marginBottom: 10 }}
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <input
          style={{ width: "100%", padding: 10, marginBottom: 10 }}
          type="password"
          placeholder="Senha"
          value={senha}
          onChange={(e) => setSenha(e.target.value)}
        />

        {erro && <p style={{ color: "red" }}>{erro}</p>}

        <button style={{ width: "100%", padding: 10 }} type="submit">
          Entrar
        </button>
      </form>
    </main>
  );
}
