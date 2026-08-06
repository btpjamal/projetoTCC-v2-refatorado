import { useNavigate, useParams } from "react-router";
import "./css/Recommendations.css";
import {useState} from "react";
import {api} from "../api/api.js";

export default function RecommendationDetails() {
    const { hobbyId } = useParams();
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    const [plano, setPlano] = useState(null);


    async function gerarPlano() {
        try {
            setLoading(true);

            const userId = localStorage.getItem("userId");
            const token = localStorage.getItem("token");

            const response = await api.post(
                `/recommendations/${userId}/${hobbyId}/initial-plan`,
                {},
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );

            setPlano(response.data);
        } catch (error) {
            console.error(error);
        } finally {
            setLoading(false);
        }
    }

    return (
      <main className="details-container">
          <section className="details-card">
              <h1>Detalhes da recomendação</h1>

              <p>Hobby Selecionado: {hobbyId}</p>

              <button onClick={gerarPlano}>
                  Gerar PLano Inicial
              </button>
              {
                  loading && (
                      <p>Gerando plano inicial...</p>
                  )
              }

              {
                  plano && (
                      <section className="plan-section">
                          <h2>{plano.hobbyNome}</h2>

                          <pre>
                              {plano.planoInicial}
                          </pre>
                      </section>
                  )
              }

              <button onClick={() => navigate("/recommendations")}>
                  Voltar
              </button>
          </section>
      </main>
    );
}