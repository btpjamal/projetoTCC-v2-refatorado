import { useState } from "react";
import { useNavigate } from "react-router";
import { api } from "../api/api";
import "./css/Onboarding.css";

const perguntas = [
    {
        campo: "tempoDisponivelSemanal",
        titulo: "Quanto tempo livre você possui por semana?",
        opcoes: [
            { label: "Até 2 horas", valor: 2 },
            { label: "De 2 a 5 horas", valor: 5 },
            { label: "De 5 a 10 horas", valor: 10 },
            { label: "Mais de 10 horas", valor: 15 },
        ],
    },
    {
        campo: "orcamentoInicial",
        titulo: "Quanto pretende investir para começar?",
        opcoes: [
            { label: "Até R$ 50", valor: 50 },
            { label: "Até R$ 100", valor: 100 },
            { label: "Até R$ 300", valor: 300 },
            { label: "Mais de R$ 300", valor: 500 },
        ],
    },
    {
        campo: "nivelSocial",
        titulo: "Como você prefere praticar um hobby?",
        opcoes: [
            { label: "Sozinho", valor: "INTROVERTIDO" },
            { label: "Com outras pessoas", valor: "EXTROVERTIDO" },
            { label: "Tanto faz", valor: "AMBIVERTIDO" },
        ],
    },
    {
        campo: "nivelExperiencia",
        titulo: "Qual é sua experiência com hobbies?",
        opcoes: [
            { label: "Estou começando agora", valor: "INICIANTE" },
            { label: "Já pratiquei alguns", valor: "INTERMEDIARIO" },
            { label: "Tenho bastante experiência", valor: "AVANCADO" },
        ],
    },
    {
        campo: "nivelAtividadeFisicaDesejada",
        titulo: "Qual intensidade física você prefere?",
        opcoes: [
            { label: "Baixa", valor: "BAIXO" },
            { label: "Moderada", valor: "MODERADO" },
            { label: "Alta", valor: "ALTO" },
            { label: "Indiferente", valor: "INDIFERENTE" },
        ],
    },
    {
        campo: "ambientePreferido",
        titulo: "Onde você prefere praticar?",
        opcoes: [
            { label: "Em casa", valor: "CASA" },
            { label: "Ao ar livre", valor: "AO_AR_LIVRE" },
            { label: "Em ambiente fechado", valor: "AMBIENTE_FECHADO" },
            { label: "Tanto faz", valor: "INDIFERENTE" },
        ],
    },
    {
        campo: "formatoPreferido",
        titulo: "Qual formato combina mais com você?",
        opcoes: [
            { label: "Presencial", valor: "PRESENCIAL" },
            { label: "Remoto", valor: "REMOTO" },
            { label: "Os dois", valor: "HIBRIDO" },
            { label: "Tanto faz", valor: "INDIFERENTE" },
        ],
    },
];

export default function Onboarding() {
    const navigate = useNavigate();

    const [etapa, setEtapa] = useState(0);
    const [enviando, setEnviando] = useState(false);
    const [erro, setErro] = useState("");

    const [profile, setProfile] = useState({
        tempoDisponivelSemanal: null,
        orcamentoInicial: null,
        nivelSocial: null,
        nivelExperiencia: null,
        nivelAtividadeFisicaDesejada: null,
        ambientePreferido: null,
        formatoPreferido: null,
    });

    const perguntaAtual = perguntas[etapa];
    const progresso = ((etapa + 1) / perguntas.length) * 100;

    async function selecionarOpcao(valor) {
        const profileAtualizado = {
            ...profile,
            [perguntaAtual.campo]: valor,
        };

        setProfile(profileAtualizado);
        setErro("");

        const ultimaEtapa = etapa === perguntas.length - 1;

        if (ultimaEtapa) {
            await finalizarOnboarding(profileAtualizado);
            return;
        }

        setEtapa((etapaAtual) => etapaAtual + 1);
    }

    async function finalizarOnboarding(dados) {
        try {
            setEnviando(true);
            setErro("");

            const userId = localStorage.getItem("userId");
            const token = localStorage.getItem("token");

            if (!userId || !token) {
                navigate("/login");
                return;
            }

            console.log("Dados enviados: ", dados);
            console.log("userId: ", userId);

            await api.post(
                `/recommendation-profiles/${userId}`,
                dados,
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );

            navigate("/recommendations");
        } catch (error) {
            console.error("Erro completo: ", error);
            console.error("Status: ", error.response?.status);
            console.error("Resposta backend: ", error.response?.data);
            setErro(
                error.response?.data?.message ||
                JSON.stringify(error.response?.data) ||
                "Não foi possível salvar suas preferências."
            );
        } finally {
            setEnviando(false);
        }
    }

    function voltar() {
        if (etapa === 0) {
            navigate("/login");
            return;
        }

        setEtapa((etapaAtual) => etapaAtual - 1);
    }

    return (
        <main className="onboarding-page">
            <section className="onboarding-card">
                <div className="onboarding-progress">
                    <div
                        className="onboarding-progress-value"
                        style={{ width: `${progresso}%` }}
                    />
                </div>

                <span className="onboarding-step">
          Etapa {etapa + 1} de {perguntas.length}
        </span>

                <h1>{perguntaAtual.titulo}</h1>

                <p className="onboarding-description">
                    Escolha a opção que mais combina com você.
                </p>

                <div className="onboarding-options">
                    {perguntaAtual.opcoes.map((opcao) => {
                        const selecionada =
                            profile[perguntaAtual.campo] === opcao.valor;

                        return (
                            <button
                                type="button"
                                key={opcao.label}
                                className={`onboarding-option ${
                                    selecionada ? "selected" : ""
                                }`}
                                onClick={() => selecionarOpcao(opcao.valor)}
                                disabled={enviando}
                            >
                                {opcao.label}
                            </button>
                        );
                    })}
                </div>

                {enviando && (
                    <p className="onboarding-message">
                        Preparando suas recomendações...
                    </p>
                )}

                {erro && (
                    <p className="onboarding-error">
                        {erro}
                    </p>
                )}

                <button
                    type="button"
                    className="onboarding-back"
                    onClick={voltar}
                    disabled={enviando}
                >
                    Voltar
                </button>
            </section>
        </main>
    );
}