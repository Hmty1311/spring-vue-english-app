import type { QuizWrongAnswer } from "./QuizWrongAnswer";

export interface QuizResult {
    total: number;
    correct: number;
    accuracy: number;
    wrongAnswers: QuizWrongAnswer[];
}