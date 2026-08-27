import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { api } from "../../api/api";
import "../../pages/css/Recommendations.css";

export default function InterestedTab() {

    const navigate = useNavigate();

    const [hobbies, setHobbies] = useState([]);
    const [loading, setLoading] = useState(true);
    const [erro, setErro] = useState("");

    useEffect(() => {
        carregarInteressados();
    }, []);

    async function carregarInteressados() {
        try {
            setLoading(true);
            setErro("");

            const userId = localStorage.getItem("userId");
            const token = localStorage.getItem("token");

            const response = await api.get(
                `/recommendation-feedbacks/${userId}?tipo=INTERESSADO`,
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );

            setHobbies(response.data);

        } catch (error) {
            console.error(
                "Erro ao carregar hobbies interessados:",
                error
            );

            setErro(
                "Não foi possível carregar seus hobbies de interesse."
            );
        } finally {
            setLoading(false);
        }
    }
    async function devolverParaDescobrir(hobbyId) {
                try {
                    const userId = localStorage.getItem("userId");
                    const token = localStorage.getItem("token");

                    await api.delete(
                        `/recommendation-feedbacks/${userId}/${hobbyId}`,
                        {
                            headers: {
                                Authorization: `Bearer ${token}`,
                            },
                        }
                    );

                    setHobbies((atuais) =>
                        atuais.filter(
                            (hobby) => hobby.hobbyId !== hobbyId
                        )
                    );

                } catch (error) {
                    console.error(
                        "Erro ao desfazer decisão:",
                        error
                    );

                    setErro(
                        "Não foi possível desfazer sua escolha."
                    );
                }
            }

    if (loading) {
        return <p>Carregando hobbies...</p>;
    }

    if (erro) {
        return <p>{erro}</p>;
    }

    if (hobbies.length === 0) {
        return (
            <section>
                <h2>Tenho interesse</h2>

                <p>
                    Você ainda não marcou nenhum hobby como interessante.
                </p>
            </section>
        );
    }

    return (
        <section>
            <h2>Tenho interesse</h2>

            <div className="recommendations-grid">
                {hobbies.map((hobby) => (
                    <div
                        key={hobby.hobbyId}
                        className="recommendation-card"
                        onClick={() =>
                            navigate(
                                `/recommendations/${hobby.hobbyId}`
                            )
                        }
                    >
                        <h3>{hobby.nome}</h3>

                        <p>{hobby.descricao}</p>

                        <p>
                            <strong>Categoria:</strong>{" "}
                            {hobby.categoria}
                        </p>


                        <button
                            type="button"
                            onClick={(e) => {
                                 e.stopPropagation();

                                 devolverParaDescobrir(
                                      hobby.hobbyId
                                 );
                            }}
                        >
                            Mudei de ideia
                        </button>
                    </div>
                ))}
            </div>
        </section>
    );
}