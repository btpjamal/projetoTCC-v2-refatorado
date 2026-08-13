import { useState } from "react";
import { useNavigate } from "react-router";
import { api } from "../api/api";

export default function Login() {
  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
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

      const { token, userId, nome, emailUsuario } = response.data;

      localStorage.setItem("token", token);
      localStorage.setItem("userId", userId);
      localStorage.setItem("nome", nome);
      localStorage.setItem("email", emailUsuario);

      const statusResponse = await api.get(
          `/recommendation-profiles/${userId}/status`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
      );

      if (statusResponse.data === true) {
        navigate("/recommendations");
      } else {
        navigate("/onboarding");
      }

    } catch (error) {
      console.error(error);

      setErro(
          error.response?.data?.message ||
          "Não foi possível realizar o login."
      );
    }
  }

      return (
          <main style={{maxWidth: 400, margin: "80px auto", fontFamily: "Arial"}}>
            <h1>Entrar</h1>

            <form onSubmit={handleLogin}>
              <input
                  style={{width: "100%", padding: 10, marginBottom: 10}}
                  type="email"
                  placeholder="Email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
              />

              <input
                  style={{width: "100%", padding: 10, marginBottom: 10}}
                  type="password"
                  placeholder="Senha"
                  value={senha}
                  onChange={(e) => setSenha(e.target.value)}
              />

              {erro && <p style={{color: "red"}}>{erro}</p>}

              <button style={{width: "100%", padding: 10}} type="submit">
                Entrar
              </button>
              <button
                  type="button"
                  onClick={() => navigate("/register")}
                  style={{
                    width: "100%",
                    padding: 10,
                    marginTop: 10,
                    background: "transparent",
                    border: "1px solid #ccc",
                    cursor: "pointer"
                  }}
              >
                Criar conta
              </button>
            </form>
          </main>
      );
    }
