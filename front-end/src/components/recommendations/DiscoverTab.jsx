import { useEffect, useState } from "react";
import { api } from "../../api/api";
import { useNavigate } from "react-router";
import "../../pages/css/Recommendations.css";


export default function DiscoverTab() {

    const navigate = useNavigate();

    const [recommendations, setRecommendations] = useState([]);
    const [loading, setLoading] = useState(true);
    const [erro, setErro] = useState(null);

    async function registrarFeedback(hobbyId, tipo) {
                try {
                    const userId = localStorage.getItem("userId");
                    const token = localStorage.getItem("token");

                    await api.post(
                        `/recommendation-feedbacks/${userId}/${hobbyId}`,
                        { tipo },
                        {
                            headers: {
                                Authorization: `Bearer ${token}`,
                            },
                        }
                    );

                    setRecommendations((atuais) =>
                        atuais.filter((recommendation) => recommendation.hobbyId !== hobbyId)
                    );

                } catch (error) {
                    console.error("Erro ao registrar feedback:", error);
                }
            }

    useEffect(() => {
        async function carregarRecomendacoes() {
          try {
            setLoading(true);

            const token = localStorage.getItem("token");
            const userId = localStorage.getItem("userId");

            if (!token || !userId) {
                localStorage.clear();
                navigate("/login");
                return;
            }

            const response = await api.get(`/recommendations/${userId}`, {
                headers: {
                  Authorization: `Bearer ${token}`,
                },
              },
            );

            console.log("Recomendações recebidas:", response.data);
            setRecommendations(response.data);
          } catch (error) {
            console.error(error);
            console.error("status:", error.response?.status);
            console.error("Resposta:", error.response?.data);

              if (error.response?.status === 401 || error.response?.status === 403) {
                  localStorage.clear();
                  navigate("/login");
                  return;
              }

            if (error.response?.status === 404) { navigate("/profile"); return; }
            setErro("Não foi possível carregar as recomendações");
          } finally {
            setLoading(false);
          }
        }

        carregarRecomendacoes();
      }, [navigate]);

      if (loading) {
          return (
              <main className="recommendations-page">
                  <div className="recommendation-loading">
                      <p>Carregando recomendações...</p>
                  </div>
              </main>
          );
      }

      if (erro) {
          return (
              <main className="recommendations-page">
                  <div className="recommendation-error">
                      <p>{erro}</p>
                  </div>
              </main>
          );
      }

        return (
            <main className="recommendations-page">
                <div className="recommendations-container">
                    <header className="recommendations-header">
                        <div>
                            <p className="recommendations-subtitle">
                                Hobbies sugeridos com base no seu perfil, interesses e preferências.
                            </p>
                        </div>


                    </header>

                    {recommendations.length === 0 ? (
                        <div className="recommendation-empty">
                            <h2>Nenhuma recomendação encontrada</h2>
                            <p>
                                Ainda não encontramos sugestões para o seu perfil.
                            </p>
                        </div>
                    ) : (
                        <section className="recommendations-grid">
                            {recommendations.map((item) => (
                                <article className="recommendation-card"
                                         key={item.hobbyId}
                                         /* dá pra colocar algum redirecionamento aqui */
                                         /* pra ter alguma interação na tela de descobrimentos */
                                         /* alguma imagem gerada por i.a, talvez */
                                         onClick={() => navigate()}
                                >
                                    <div className="recommendation-card-header">
                    <span className="recommendation-category">
                      {item.categoria}
                    </span>

                                        <span className="recommendation-score">
                      {item.score} pts
                    </span>
                                    </div>

                                    <h2 className="recommendation-name">{item.nome}</h2>

                                    <p className="recommendation-description">
                                        {item.descricao}
                                    </p>

                                    <div className="recommendation-reason">
                                        {(item.motivos || []).slice(0, 3).map((motivo) => <p key={motivo}>• {motivo}</p>)}
                                        {(item.alertas || []).slice(0, 1).map((alerta) => <p key={alerta}>⚠ {alerta}</p>)}
                                    </div>


                                    <button
                                        type="button"
                                        onClick={(e) => {
                                            e.stopPropagation();

                                            registrarFeedback(
                                                item.hobbyId,
                                                "NAO_INTERESSADO"
                                            );
                                        }}
                                    >
                                        Não me interessa
                                    </button>

                                    <button
                                        type="button"
                                        onClick={(e) => {
                                            e.stopPropagation();

                                            registrarFeedback(
                                                item.hobbyId,
                                                "INTERESSADO"
                                            );
                                        }}
                                    >
                                        Tenho interesse
                                    </button>


                                </article>
                            ))}
                        </section>
                    )}
                </div>
            </main>
        );
    }
